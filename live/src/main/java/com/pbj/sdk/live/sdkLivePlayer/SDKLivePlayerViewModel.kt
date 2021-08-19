package com.pbj.sdk.live.sdkLivePlayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.live.livePlayer.LiveUpdateListener
import org.koin.core.component.inject
import timber.log.Timber

internal class SDKLivePlayerViewModel : ViewModel(), LiveUpdateListener, LiveKoinComponent {

    private val liveInteractor: LiveInteractor by inject()

    private val guestInteractor: GuestInteractor by inject()

    private var isPlaying = false

    private var episode: Episode? = null

    private var show: Show? = null

    val screenState = MutableLiveData<State>(State.Loading)

    var user: User? = null

    fun init(showId: String?) {
        getLiveStreamAsGuest(showId)
        listenToEpisode()
    }

    private fun getLiveStreamAsGuest(showId: String?) {
        screenState.postValue(State.Loading)
        guestInteractor.authenticateAsGuest({
            onError(it.localizedMessage)
        }) {
            if (showId.isNullOrBlank())
                getAnyEpisode()
            else
                getShow(showId)
        }
    }

    private fun getShow(showId: String) {
        liveInteractor.getCurrentEpisode(showId, {
            onError(it.localizedMessage)
        }) {
            it?.let {
                episode = it
                updateRoomState(it)
            } ?: run {
                getShowPublic(showId)
            }
        }
    }

    private fun getAnyEpisode() {
        liveInteractor.getCurrentEpisode({
            onError(it.localizedMessage)
        }) {
            it?.let {
                episode = it
                updateRoomState(it)
            } ?: run {
                updateRoomState(null)
            }
        }
    }

    private fun getShowPublic(showId: String) {
        liveInteractor.getShowPublic(showId, {
            onError(it.localizedMessage)
        }) { showResponse ->
            showResponse?.let {
                show = it
                updateRoomState(null)
            }
        }
    }

    fun onError(message: String?) {
        screenState.postValue(State.Error(message))
        Timber.e(message)
    }

    override fun listenToEpisode() {
        liveInteractor.registerForRealTimeLiveStreamUpdates {
            Timber.d(it.status.toString())

            episode?.let { live ->
                if (live.id == it.id)
                    onLiveUpdate(it)
            } ?: run {
                if (it.showId == show?.id)
                    onShowUpdate(it)
            }
        }
    }

    private fun onShowUpdate(episodeStatusUpdate: EpisodeStatusUpdate) {
        if (episode == null) {
            episodeStatusUpdate.apply {
                episode =
                    Episode(id, description = waitingRoomDescription, status = status, show = show)
            }
        }

        onLiveUpdate(episodeStatusUpdate)
    }

    private fun onLiveUpdate(episodeStatusUpdate: EpisodeStatusUpdate) {
        episodeStatusUpdate.apply {
            if (status != episode?.status || waitingRoomDescription != episode?.description) {
                episode = episode?.copy(
                    description = waitingRoomDescription,
                    status = status
                )

                episode?.let {
                    updateRoomState(it)
                }
            }
        }
    }

    private fun updateRoomState(live: Episode?) {

        val hasEpisodePlaying = isPlaying && live != null

        val state: State = when {
            hasEpisodePlaying || live?.isBroadcasting == true -> State.HasEpisode(live!!)
            !isPlaying && live?.status == EpisodeStatus.Finished -> State.EpisodeEnd(live)
            show != null -> State.HasShow(show!!)
            else -> State.NoLive
        }

        if (state is State.HasEpisode && screenState.value is State.HasEpisode) {
            return
        }

        screenState.postValue(state)

        Timber.d(state.toString())
    }

    fun onLiveFinished() {
        episode?.let {
            if(isPlaying) {
                isPlaying = false
                updateRoomState(it)
            }
        }
    }

    fun onLiveReady() {
        episode?.let {
            if(!isPlaying) {
                isPlaying = true
                updateRoomState(it)
            }
        }
    }

    fun onLiveLoad() {
        screenState.postValue(State.Loading)
    }

    sealed class State {
        object Loading : State()
        object NoLive : State()
        class HasEpisode(val episode: Episode) : State()
        class HasShow(val show: Show) : State()
        class EpisodeEnd(val episode: Episode?) : State()
        class Error(val message: String? = null) : State()
    }
}
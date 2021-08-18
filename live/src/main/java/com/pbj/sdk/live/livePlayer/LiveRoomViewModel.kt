package com.pbj.sdk.live.livePlayer

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.core.SdkHolder
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.chat.LiveChatSource
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.vod.model.VodVideo
import com.pbj.sdk.notifications.LiveNotificationManager
import com.pbj.sdk.product.ProductFeature
import com.pbj.sdk.utils.eventBus.LiveEventBus
import com.pbj.sdk.utils.eventBus.LiveNotificationModified
import com.pbj.sdk.utils.launch
import kotlinx.coroutines.flow.collect
import org.koin.core.component.inject
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit


internal class LiveRoomViewModel : ViewModel(), LiveUpdateListener, LiveKoinComponent {

    private val tracker: AnalyticsTracker by inject()
    private val liveInteractor: LiveInteractor by inject()
    private val userInteractor: UserInteractor by inject()
    private val productFeature: ProductFeature by inject()

    var liveChatSource: LiveChatSource? = null

    var liveNotificationManager: LiveNotificationManager? = null

    var episode: Episode? = null

    var nextLiveStream = MutableLiveData<Episode?>(null)

    val streamUrl = MutableLiveData<BroadcastUrl?>(null)

    val liveRoomState = MutableLiveData(LiveRoomState.Idle)

    val remainingTime = MutableLiveData("")

    val productList = MutableLiveData<List<Product>>(listOf())

    val highlightedProductList = MutableLiveData<List<Product>>(listOf())

    private var isPlaying = false

    private var countdownTimer: CountDownTimer? = null

    var messageList = MutableLiveData<List<ChatMessage>>(listOf())

    var error = MutableLiveData<Throwable?>(null)

    var user: User? = null

    var guestUsername: String? = null

    var isVideo: Boolean = false

    fun init(live: Episode?, nextLive: Episode?) {
        episode = live
        nextLiveStream.value = nextLive
        isVideo = episode?.video != null
        fetchUser()
        initialize()
    }

    private fun initialize() {

        listenToNotificationSubscriptions()

        initStreamUpdates()

        liveNotificationManager = SdkHolder.instance.liveNotificationManager
        liveChatSource = SdkHolder.instance.liveChatSource

        liveNotificationManager?.init()

        episode?.let {
            if (isVideo)
                it.video?.let { video ->
                    getProducts(video)
                }
            else {
                getProducts(it)
                getHighlightedProducts(it)
                registerForProductHighlights(it)
            }
        }

        initCountdown()
    }

    private fun initCountdown() {
        episode?.startDate?.let {
            startCountdown(it)
        } ?: run {
            remainingTime.value = "00  00  00"
        }
    }

    private fun initStreamUpdates() {
        episode?.let {
            updateLiveRoomState(it)
            listenToEpisode()
            listenToChat(it)
        }
    }

    override fun listenToEpisode() {
        liveInteractor.registerForRealTimeLiveStreamUpdates {

            Timber.d(it.status.toString())

            if (episode?.id == it.id) {
                onLiveUpdate(it)
            }
            if (nextLiveStream.value?.id == it.id) {
                nextLiveStream.postValue(
                    nextLiveStream.value?.copy(
                        description = it.waitingRoomDescription,
                        status = it.status
                    )
                )
            }
        }
    }

    private fun onLiveUpdate(episodeStatusUpdate: EpisodeStatusUpdate) {
        episodeStatusUpdate.apply {
            if (status != episode?.status || waitingRoomDescription != episode?.description) {

                val show = episode?.show ?: Show(episodeStatusUpdate.showId)

                episode = episode?.copy(
                    status = status,
                    show = show.copy(waitingRoomDescription = waitingRoomDescription)
                )

                episode?.let {
                    updateLiveRoomState(it)
                }
            }
        }
    }

    fun sendMessage(message: String) {
        launch {
            val username = user?.username ?: guestUsername
            username?.let {
                postMessage(it, message)
                episode?.let { live -> tracker.logChatMessageSent(live) }
            }
        }
    }

    private suspend fun postMessage(username: String, message: String) {
        episode?.let {
            liveChatSource?.postChatMessage(username, message, it)
        }
    }

    private fun listenToChat(episode: Episode) {
        launch {
            liveChatSource?.getChatMessages(episode)?.collect {
                messageList.postValue(it)
            }
        }
    }

    private fun getStreamUrl(live: Episode) {
        if (live.isBroadcasting && streamUrl.value == null) {
            liveInteractor.getBroadcastUrl(live, {
                error.postValue(it)
            }) {
                error.postValue(null)
                streamUrl.postValue(it)
            }
        }
    }

    private fun updateLiveRoomState(live: Episode) {

        val isBroadcastingOrFinished = live.isBroadcasting || live.isFinished

        val roomState = when {
            isPlaying && isBroadcastingOrFinished -> LiveRoomState.Playing
            live.isActive -> LiveRoomState.Active
            live.isFinished && !isPlaying -> LiveRoomState.Finished
            else -> LiveRoomState.Idle
        }

        liveRoomState.postValue(roomState)

        Timber.d(roomState.toString())

        if (liveRoomState.value == LiveRoomState.Finished) {
            nextLiveStream.value?.endDate?.let {
                startCountdown(it)
            }
        }

        episode?.let {
            getStreamUrl(it)
        }
    }

    private fun startCountdown(end: OffsetDateTime) {
        stopTimer()
        val currentTime = System.currentTimeMillis()
        val duration = end.toInstant().toEpochMilli() - currentTime
        launch {
            countdownTimer = object : CountDownTimer(duration, SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTime.postValue(formatString(millisUntilFinished))
                }

                override fun onFinish() {}
            }.start()
        }
    }

    private fun formatString(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis) % 60
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        return "${formatResult(hours)}  ${formatResult(minutes)}  ${formatResult(seconds)}"
    }

    private fun formatResult(number: Long): String {
        return when {
            number == 0L -> "00"
            number < 10 -> "0$number"
            else -> number.toString()
        }
    }

    private fun stopTimer() {
        countdownTimer?.cancel()
    }

    fun toggleReminderFor(episode: Episode) {
        liveNotificationManager?.toggleReminderFor(episode)
    }

    val isReminderSet: Boolean
        get() = nextLiveStream.value?.hasReminder ?: false

    private fun listenToNotificationSubscriptions() {
        launch {
            LiveEventBus.listen<LiveNotificationModified>().collect {
                if (nextLiveStream.value?.id == it.episodeId) {
                    nextLiveStream.postValue(nextLiveStream.value?.copy(hasReminder = it.isReminderSet))
                }
            }
        }
    }

    private fun getProducts(episode: Episode) {
        productFeature.getProductsFor(episode, {
            Timber.e(it)
        }) {
            productList.postValue(it)
        }
    }

    private fun getProducts(video: VodVideo) {
        productFeature.getProductsFor(video, {
            Timber.e(it)
        }) {
            productList.postValue(it)
        }
    }

    private fun getHighlightedProducts(episode: Episode) {
        productFeature.getHighlightedProducts(episode, {
            Timber.e(it)
        }) {
            highlightedProductList.postValue(it)
        }
    }

    private fun registerForProductHighlights(episode: Episode) {
        productFeature.registerForProductHighlights(episode) {
            Timber.d("Highlighted Products updated with: ${it.productList.count()}")
            highlightedProductList.postValue(it.productList)
        }
    }

    private fun unRegisterForProductHighlights() {
        episode?.let {
            productFeature.unRegisterProductHighlights(it)
        }
    }

    private fun fetchUser() {
        userInteractor.getLocalUser {
            user = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        unRegisterForProductHighlights()
    }

    fun onLiveFinished() {
        episode?.let {
            isPlaying = false
            updateLiveRoomState(it)
        }
    }

    fun onLiveReady() {
        episode?.let {
            isPlaying = true
            updateLiveRoomState(it)
        }
    }

    fun logOnClickProduct(product: Product) {
        tracker.logFeaturedProductClicked(product)
    }

    fun changeProductHighLighting(id: String, shouldShow: Boolean) {
        val highlightedProducts = highlightedProductList.value?.toMutableList() ?: mutableListOf()
        if (shouldShow) {
            val product = productList.value?.firstOrNull { it.id == id }
            product?.let {
                highlightedProducts.add(it)
                highlightedProductList.postValue(highlightedProducts)
            }
        } else {
            highlightedProductList.postValue(
                highlightedProducts.filter { product ->
                    product.id != id
                }
            )
        }
    }

    companion object {
        private const val SECOND = 1000L
    }

    enum class LiveRoomState {
        NoLiveStream,
        Idle,
        Active,
        Playing,
        Finished
    }
}

internal val LiveRoomViewModel.LiveRoomState.isValidStateForBody: Boolean
    get() = this == LiveRoomViewModel.LiveRoomState.Idle || this == LiveRoomViewModel.LiveRoomState.Active

internal val LiveRoomViewModel.LiveRoomState.isValidStateForChat: Boolean
    get() = this == LiveRoomViewModel.LiveRoomState.Playing || this == LiveRoomViewModel.LiveRoomState.Active
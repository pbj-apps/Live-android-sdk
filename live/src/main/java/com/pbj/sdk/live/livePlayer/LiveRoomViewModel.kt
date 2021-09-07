package com.pbj.sdk.live.livePlayer

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.common.ui.PlayerSettings
import com.pbj.sdk.common.ui.VideoState
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

    var episode: Episode? by mutableStateOf(null)

    var nextLiveStream: Episode? by mutableStateOf(null)

    var streamUrl: BroadcastUrl? by mutableStateOf(null)

    var remainingTime: String by mutableStateOf("")

    var productList by mutableStateOf<List<Product>>(listOf())

    var highlightedProductList by mutableStateOf<List<Product>>(listOf())

    var isPlaying by mutableStateOf(false)

    var playerSettings: PlayerSettings by mutableStateOf(PlayerSettings())

    private var countdownTimer: CountDownTimer? = null

    var messageList by mutableStateOf<List<ChatMessage>>(listOf())

    var error by mutableStateOf<Throwable?>(null)

    var user: User? = null

    var chatText: String by mutableStateOf("")

    var guestUsername: String? = null

    var isVideo: Boolean = false

    fun init(live: Episode?, nextLive: Episode?) {
        episode = live
        nextLiveStream = nextLive
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

            if (it.isBroadcasting)
                getStreamUrl(it)
        }

        initCountdown()
    }

    private fun initCountdown() {
        episode?.startDate?.let {
            startCountdown(it)
        } ?: run {
            remainingTime = "00  00  00"
        }
    }

    private fun initStreamUpdates() {
        episode?.let {
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
            if (nextLiveStream?.id == it.id) {
                nextLiveStream = nextLiveStream?.copy(
                    status = it.status
                )
            }
        }
    }

    private fun onLiveUpdate(episodeStatusUpdate: EpisodeStatusUpdate) {
        episodeStatusUpdate.apply {
            if (status != episode?.status || waitingRoomDescription != episode?.show?.waitingRoomDescription) {

                val show = episode?.show ?: Show(episodeStatusUpdate.showId)

                episode = episode?.copy(
                    status = status,
                    show = show.copy(waitingRoomDescription = waitingRoomDescription)
                )

                episode?.let {
                    when (it.status) {
                        EpisodeStatus.Broadcasting -> getStreamUrl(it)
                        EpisodeStatus.Finished -> nextLiveStream?.endDate?.let { time ->
                            startCountdown(time)
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    fun onChatTextChange(text: String) {
        chatText = text
    }

    fun sendMessage() {
        launch {
            val username = user?.username ?: guestUsername
            username?.let {
                postMessage(it, chatText)
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
                messageList = it
            }
        }
    }

    private fun getStreamUrl(live: Episode) {
        if (streamUrl == null) {
            liveInteractor.getBroadcastUrl(live, {
                error = it
            }) {
                error = null
                streamUrl = if (isVideo)
                    BroadcastUrl(
                        broadcastUrl = episode?.video?.videoURL,
                        elapsedTime = it?.elapsedTime
                    )
                else it
            }
        }
    }

    private fun startCountdown(end: OffsetDateTime) {
        stopTimer()
        val currentTime = System.currentTimeMillis()
        val duration = end.toInstant().toEpochMilli() - currentTime
        launch {
            countdownTimer = object : CountDownTimer(duration, SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTime = formatString(millisUntilFinished)
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

    private fun listenToNotificationSubscriptions() {
        launch {
            LiveEventBus.listen<LiveNotificationModified>().collect {
                if (nextLiveStream?.id == it.episodeId) {
                    nextLiveStream = nextLiveStream?.copy(hasReminder = it.isReminderSet)
                }
            }
        }
    }

    private fun getProducts(episode: Episode) {
        productFeature.getProductsFor(episode, {
            Timber.e(it)
        }) { list ->
            list?.let {
                productList = it
            }
        }
    }

    private fun getProducts(video: VodVideo) {
        productFeature.getProductsFor(video, {
            Timber.e(it)
        }) { list ->
            list?.let {
                productList = it
            }
        }
    }

    private fun getHighlightedProducts(episode: Episode) {
        productFeature.getHighlightedProducts(episode, {
            Timber.e(it)
        }) {
            highlightedProductList = it ?: listOf()
        }
    }

    private fun registerForProductHighlights(episode: Episode) {
        productFeature.registerForProductHighlights(episode) {
            Timber.d("Highlighted Products updated with: ${it.productList.count()}")
            highlightedProductList = it.productList
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

    fun onPlayerStateChange(playerState: VideoState) {
        when (playerState) {
            VideoState.READY -> onLiveReady()
            VideoState.ENDED -> onLiveFinished()
        }
    }

    fun onScreenRotation(isLandscape: Boolean) {
        playerSettings = if (isLandscape)
            playerSettings.copy(
                resizeMode = PlayerSettings.ResizeMode.Fill,
                scalingMode = PlayerSettings.ScalingMode.FitWithCropping
            )
        else
            playerSettings.copy(
                resizeMode = PlayerSettings.ResizeMode.Fit,
                scalingMode = PlayerSettings.ScalingMode.Fit
            )
    }

    fun onLiveFinished() {
        isPlaying = false
        episode = episode?.copy(status = EpisodeStatus.Finished)
    }

    fun onLiveReady() {
        isPlaying = true
    }

    fun logOnClickProduct(product: Product) {
        tracker.logFeaturedProductClicked(product)
    }

    fun changeProductHighLighting(id: String, shouldShow: Boolean) {
        val highlightedProducts = highlightedProductList.toMutableList()
        if (shouldShow) {
            val product = productList.firstOrNull { it.id == id }
            product?.let {
                highlightedProducts.add(it)
                highlightedProductList = highlightedProducts
            }
        } else {
            highlightedProductList = highlightedProducts.filter { product ->
                product.id != id
            }
        }
    }

    companion object {
        private const val SECOND = 1000L
    }
}
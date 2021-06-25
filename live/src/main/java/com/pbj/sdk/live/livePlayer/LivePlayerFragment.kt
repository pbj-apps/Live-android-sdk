package com.pbj.sdk.live.livePlayer

import android.app.PictureInPictureParams
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.pbj.sdk.R
import com.pbj.sdk.databinding.FragmentLivePlayerBinding
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.product.ProductAdapter
import com.pbj.sdk.utils.observe
import com.pbj.sdk.utils.startFragment
import com.pbj.sdk.videoPlayer.VideoPlayerFragment
import timber.log.Timber


internal class LivePlayerFragment : Fragment(), VideoPlayerFragment.LiveFragmentListener,
    ProductAdapter.OnProductClickListener {

    interface Listener {
        fun onPressClose()

        fun enableScreenRotation(enable: Boolean)

        fun onPlayerError(errorMessage: String?)
    }

    private lateinit var view: FragmentLivePlayerBinding

    private val vm: LiveRoomViewModel by viewModels()

    private var episode: Episode? = null

    private var nextEpisode: Episode? = null

    private lateinit var chatAdapter: ChatAdapter

    private var productAdapter: ProductAdapter = ProductAdapter(this)

    private var isChatVisible = false

    private var showProducts = false

    private var hasProducts = false

    private var isBroadcastingOrPlaying = false

    private var listener: Listener? = null

    private var productList: List<Product> = listOf()

    private var highlightedProductList: List<Product> = listOf()

    override fun onStart() {
        super.onStart()

        if (activity is Listener) {
            listener = activity as Listener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            episode = getParcelable(LIVE_STREAM)
            nextEpisode = getParcelable(NEXT_LIVE_STREAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentLivePlayerBinding.inflate(inflater)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.init(episode, nextEpisode)

        vm.episode?.let {
            initView(it)
        }

        observeViewModel()
    }

    private fun initView(episode: Episode) {
        view.apply {

            bgImage.load(episode.fullSizeImage)

            vm.liveChatSource?.let {
                initChatViews()
            } ?: run {
                footer.isVisible = false
                chatListView.isVisible = false
            }

            initProductList()

            root.setOnClickListener {
                if (vm.liveRoomState.value != LiveRoomViewModel.LiveRoomState.ENDED) {
                    overlay.isVisible = !overlay.isVisible
                }
            }

            closeIcon.setOnClickListener {
                Timber.e("CLose")
                listener?.onPressClose()
            }
        }

        setUpEndBody()

        vm.streamUrl.value?.let {
            initVideoPlayer(it)
        }

        vm.liveRoomState.value?.let {
            updateView(it)
        }
    }

    private fun initChatViews() {
        view.apply {
            chatAdapter = ChatAdapter(vm.messageList.value ?: listOf())

            chatListView.adapter = chatAdapter
            chatListView.layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }

            chatListView.setOnClickListener {
                overlay.isInvisible = !overlay.isInvisible
            }

            chatButton.setOnClickListener {
                toggleChatVisibility()
            }

            chatInputLayout.setEndIconOnClickListener {
                sendMessage(chatInputText.text.toString())
            }

            chatInputText.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        sendMessage(v.text.toString())
                        v.text = ""
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initProductList() {
        view.apply {
            productListView.apply {
                adapter = productAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            productCount.text = productList.toString()

            productButton.setOnClickListener {
                toggleProductListVisibility()
            }
        }
    }

    private fun updateView(liveRoomState: LiveRoomViewModel.LiveRoomState) {
        setStreamTitle(liveRoomState)
        setDescription(vm.episode)

        isChatVisible = false

        view.apply {

            if (liveRoomState == LiveRoomViewModel.LiveRoomState.ENDED) {
                overlay.isVisible = true
            }

            vm.episode?.let { live ->

                updateHeader(liveRoomState)

                activeBody.isVisible = live.isBeforeBroadcast

                endBody.isVisible = liveRoomState == LiveRoomViewModel.LiveRoomState.ENDED
                        && vm.nextLiveStream.value != null

                updateChatView(liveRoomState)

                isBroadcastingOrPlaying = live.isBroadcasting
                        || liveRoomState == LiveRoomViewModel.LiveRoomState.PLAYING

                bgImage.isVisible = !isBroadcastingOrPlaying

                updateProductButtonVisibility()

                toggleProductListVisibility(false)

                videoPlayerContainer.isVisible = isBroadcastingOrPlaying

                listener?.enableScreenRotation(isBroadcastingOrPlaying)
            }
        }
    }

    private fun updateHeader(liveRoomState: LiveRoomViewModel.LiveRoomState) {
        view.apply {
            vm.episode?.let { live ->
                val icon = when {
                    liveRoomState == LiveRoomViewModel.LiveRoomState.PLAYING || live.isBroadcasting -> R.drawable.ic_live_on
                    liveRoomState == LiveRoomViewModel.LiveRoomState.ENDED -> R.drawable.ic_chevron_left
                    else -> R.drawable.ic_up_next
                }

                if (liveRoomState == LiveRoomViewModel.LiveRoomState.NO_LIVESTREAM)
                    updateNoLive()

                closeIcon.isVisible = liveRoomState != LiveRoomViewModel.LiveRoomState.ENDED

                liveIcon.apply {
                    background = ContextCompat.getDrawable(context, icon)

                    setOnClickListener {
                        if (liveRoomState == LiveRoomViewModel.LiveRoomState.ENDED) {
                            listener?.onPressClose()
                        }
                    }
                }
            }
        }
    }

    private fun updateNoLive() {
        Timber.d("No Live")
        view.apply {
            countdown.isVisible = false
            countdownTitle.isVisible = false
            activeBody.isVisible = false
        }
    }

    private fun updateChatView(liveRoomState: LiveRoomViewModel.LiveRoomState) {
        view.apply {
            vm.liveChatSource?.let {
                chatMessageCount.text = vm.messageList.value?.count().toString()

                chatButton.isVisible =
                    liveRoomState.isValidStateForChat && vm.episode?.isChatEnabled == true

                chatInputLayout.isVisible = isChatVisible
                chatListView.isVisible = isChatVisible
            }
            footer.isVisible =
                liveRoomState != LiveRoomViewModel.LiveRoomState.ENDED && vm.liveChatSource != null
        }
    }

    private fun setUpEndBody() {
        vm.nextLiveStream.value?.apply {
            view.nextStreamTitle.text = title?.toUpperCase()
            view.nextCoachImage.load(streamer?.profileImage?.medium)
            val coachName = "${streamer?.firstName} ${streamer?.lastName}"
            view.nextCoachName.text = getString(R.string.with_coach, coachName)

            view.remindButton.setOnClickListener {
                vm.toggleReminderFor(this)
            }

            setRemindButtonText()
        }
    }

    private fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            vm.sendMessage(message)
            view.chatInputText.setText("")
        }
    }

    private fun toggleChatVisibility(show: Boolean? = null) {
        if (showProducts)
            toggleProductListVisibility()

        isChatVisible = show ?: !isChatVisible

        view.apply {
            chatMessageCount.isVisible = !isChatVisible
            chatInputLayout.isVisible = isChatVisible
            chatListView.isVisible = isChatVisible

            updateProductButtonVisibility()

            if (vm.liveRoomState.value?.isValidStateForBody == true && vm.episode?.isBroadcasting == false) {
                activeBody.isVisible = !isChatVisible
            }
        }
    }

    private fun toggleProductListVisibility(show: Boolean? = null) {

        showProducts = show ?: !showProducts

        val productsToDisplay = if (showProducts)
            productList
        else
            highlightedProductList

        productAdapter.update(productsToDisplay)

        view.apply {
            productListView.isVisible =
                (showProducts || highlightedProductList.isNotEmpty()) && isBroadcastingOrPlaying
        }

        if (isChatVisible)
            toggleChatVisibility(!showProducts)
    }

    private fun updateProductButtonVisibility() {
        view.apply {
            productButton.isVisible = hasProducts && !isChatVisible && isBroadcastingOrPlaying
        }
    }

    private fun setStreamTitle(liveState: LiveRoomViewModel.LiveRoomState) {
        val title = if (liveState == LiveRoomViewModel.LiveRoomState.ENDED) {
            getString(R.string.end_stream_title)
        } else {
            vm.episode?.title
        }

        view.title.text = title?.toUpperCase()
    }

    private fun setDescription(episode: Episode?) {
        val string = if (episode?.status == EpisodeStatus.WAITING_ROOM)
            episode?.show?.waitingRoomDescription
        else
            episode?.description

        view.description.text = string?.toUpperCase()
    }

    private fun initVideoPlayer(url: BroadcastUrl) {
        val video = if (url.broadcastUrl.isNullOrEmpty())
            vm.episode?.video?.videoURL
        else
            url.broadcastUrl

        video?.let {
            val videoFragment = VideoPlayerFragment.newInstance(
                video = it,
                isLive = true,
                timeCode = url.elapsedTime
            ).apply {
                liveFragmentListener = this@LivePlayerFragment
            }
            parentFragmentManager.startFragment(videoFragment, view.videoPlayerContainer.id)
        }

    }

    private fun observeViewModel() {
        observeRoomState()
        observeTimer()
        observeLiveUrl()
        observeNextStream()
        observeProductList()
        observeHighlightedProductList()
        observeError()

        vm.liveChatSource?.let {
            observeChatMessages()
        }

        vm.liveNotificationManager?.let {
            observeNotificationIdList()
        }
    }

    private fun observeLiveUrl() {
        observe(vm.streamUrl) {
            it?.let {
                initVideoPlayer(it)
            }
        }
    }

    private fun observeRoomState() {
        observe(vm.liveRoomState) {
            updateView(it)
        }
    }

    private fun observeTimer() {
        observe(vm.remainingTime) {
            if (vm.liveRoomState.value == LiveRoomViewModel.LiveRoomState.ENDED) {
                view.nextCountdown.text = it
            } else {
                view.countdown.text = it
            }
        }
    }

    private fun observeChatMessages() {
        observe(vm.messageList) {
            chatAdapter.update(it)
            view.apply {
                chatMessageCount.text = it.count().toString()
                chatListView.smoothScrollToPosition(it.size)
            }
        }
    }

    private fun observeNotificationIdList() {
        observe(vm.remindedLiveStreamIdList) {
            setRemindButtonText()
        }
    }

    private fun observeProductList() {
        observe(vm.productList) {

            productList = it
            hasProducts = it.count() > 0

            view.apply {
                updateProductButtonVisibility()
                productCount.text = it.count().toString()
            }
        }
    }

    private fun observeHighlightedProductList() {
        observe(vm.highlightedProductList) {
            highlightedProductList = it
            toggleProductListVisibility(showProducts)
        }
    }

    private fun observeNextStream() {
        observe(vm.nextLiveStream) {
            setUpEndBody()
        }
    }

    private fun observeError() {
        observe(vm.error) { error ->
            error?.let {
                Snackbar.make(
                    requireContext(),
                    view.videoPlayerContainer,
                    it.localizedMessage,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onClickProduct(product: Product) {
        vm.logOnClickProduct(product)
        val params = PictureInPictureParams.Builder().build()
        activity?.enterPictureInPictureMode(params)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(browserIntent)
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        if (isInPictureInPictureMode) {
            view.overlay.isVisible = false
        }
    }

    private fun setRemindButtonText() {
        view.remindButton.text = getString(
            when {
                vm.nextLiveStream.value?.isActive == true -> R.string.join_live
                vm.isReminderSet -> R.string.reminder_set
                else -> R.string.remind_me
            }
        )
    }

    override fun onLiveFinished() {
        vm.onLiveFinished()
    }

    override fun onLiveReady() {
        vm.onLiveReady()
    }

    override fun onPlayerError(errorMessage: String?) {
        listener?.onPlayerError(errorMessage)
    }

    companion object {

        private const val LIVE_STREAM = "LIVE_STREAM"
        private const val NEXT_LIVE_STREAM = "NEXT_LIVE_STREAM"

        @JvmStatic
        fun newInstance(episode: Episode?, nextEpisode: Episode?) =
            LivePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LIVE_STREAM, episode)
                    putParcelable(NEXT_LIVE_STREAM, nextEpisode)
                }
            }
    }
}
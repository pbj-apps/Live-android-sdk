package com.pbj.sdk.live.livePlayer

import android.app.PictureInPictureParams
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pbj.sdk.R
import com.pbj.sdk.databinding.FragmentLivePlayerBinding
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.product.ProductAdapter
import com.pbj.sdk.utils.*
import com.pbj.sdk.videoPlayer.VideoPlayerFragment
import java.util.*


internal class LivePlayerFragment : Fragment(), VideoPlayerFragment.LiveFragmentListener,
    ProductAdapter.OnProductClickListener {

    interface Listener {
        fun onPressClose()
        fun enableScreenRotation(enable: Boolean)
        fun onPlayerError(errorMessage: String?)
        fun onPlayerLoad()
        fun onLiveReady()
    }

    private lateinit var view: FragmentLivePlayerBinding

    private val vm: LiveRoomViewModel by viewModels()

    private var episode: Episode? = null
    private var nextEpisode: Episode? = null

    private var isChatEnabled: Boolean = false
    private lateinit var chatAdapter: ChatAdapter
    private var isChatVisible = false

    private var showProducts = false
    private var hasProducts = false

    private var isBroadcastingOrPlaying = false

    private var listener: Listener? = null

    private var productAdapter: ProductAdapter = ProductAdapter(this)
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
            isChatEnabled = getBoolean(IS_CHAT_ENABLED)
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

//        vm.episode?.let {
//            initView(it)
//        }
    }

//    private fun initView(episode: Episode) {
//        view.apply {
//
//            bgImage.load(episode.image?.fullSize)
//
//            vm.liveChatSource?.let {
//                initChatViews()
//            } ?: run {
//                footer.isVisible = false
//                chatListView.isVisible = false
//            }
//
//            initProductList()
//
//            root.setOnClickListener {
//                if (vm.liveRoomState.value != LiveRoomViewModel.LiveRoomState.Finished) {
//                    overlay.isVisible = !overlay.isVisible
//                }
//            }
//
//            closeIcon.setOnClickListener {
//                Timber.e("CLose")
//                listener?.onPressClose()
//            }
//        }
//
//        setUpEndBody()
//
//        vm.streamUrl.value?.let {
//            initVideoPlayer(it)
//        }
//
//        vm.liveRoomState.value?.let {
//            updateView(it)
//        }
//    }
//
//    private fun initChatViews() {
//        view.apply {
//            chatAdapter = ChatAdapter(vm.messageList ?: listOf())
//
//            chatListView.adapter = chatAdapter
//            chatListView.layoutManager = LinearLayoutManager(context).apply {
//                stackFromEnd = true
//            }
//
//            chatListView.setOnClickListener {
//                overlay.isInvisible = !overlay.isInvisible
//            }
//
//            chatButton.setOnClickListener {
//                toggleChatVisibility()
//            }
//
//            chatInputLayout.setEndIconOnClickListener {
//                sendMessage(chatInputText.text.toString())
//            }
//
//            chatInputText.setOnEditorActionListener { v, actionId, event ->
//                return@setOnEditorActionListener when (actionId) {
//                    EditorInfo.IME_ACTION_SEND -> {
//                        sendMessage(v.text.toString())
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }
//    }
//
//    private fun initProductList() {
//        view.apply {
//            productListView.apply {
//                adapter = productAdapter
//                layoutManager =
//                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            }
//
//            productCount.text = productList.toString()
//
//            productButton.setOnClickListener {
//                toggleProductListVisibility()
//            }
//        }
//    }
//
//    private fun updateView(liveRoomState: LiveRoomViewModel.LiveRoomState) {
//        setStreamTitle(liveRoomState)
//        setDescription(vm.episode)
//
//        isChatVisible = false
//
//        view.apply {
//
//            if (liveRoomState == LiveRoomViewModel.LiveRoomState.Finished) {
//                overlay.isVisible = true
//            }
//
//            vm.episode?.let { live ->
//
//                updateHeader(liveRoomState)
//
//                activeBody.isVisible = live.isBeforeBroadcast
//
//                endBody.isVisible = liveRoomState == LiveRoomViewModel.LiveRoomState.Finished
//                        && vm.nextLiveStream != null
//
//                updateChatView(liveRoomState)
//
//                isBroadcastingOrPlaying = live.isBroadcasting
//                        || liveRoomState == LiveRoomViewModel.LiveRoomState.Playing
//
//                bgImage.isVisible = !isBroadcastingOrPlaying
//
//                updateProductButtonVisibility()
//
//                toggleProductListVisibility(false)
//
//                videoPlayerContainer.isVisible = isBroadcastingOrPlaying
//
//                listener?.enableScreenRotation(isBroadcastingOrPlaying)
//            }
//        }
//    }
//
//    private fun updateHeader(liveRoomState: LiveRoomViewModel.LiveRoomState) {
//        view.apply {
//            vm.episode?.let { live ->
//                val icon = when {
//                    liveRoomState == LiveRoomViewModel.LiveRoomState.Playing || live.isBroadcasting -> R.drawable.ic_live_on
//                    liveRoomState == LiveRoomViewModel.LiveRoomState.Finished -> R.drawable.ic_chevron_left
//                    else -> R.drawable.ic_up_next
//                }
//
//                if (liveRoomState == LiveRoomViewModel.LiveRoomState.NoLiveStream)
//                    updateNoLive()
//
//                closeIcon.isVisible = liveRoomState != LiveRoomViewModel.LiveRoomState.Finished
//
//                liveIcon.apply {
//                    background = ContextCompat.getDrawable(context, icon)
//
//                    setOnClickListener {
//                        if (liveRoomState == LiveRoomViewModel.LiveRoomState.Finished) {
//                            listener?.onPressClose()
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun updateNoLive() {
//        Timber.d("No Live")
//        view.apply {
//            countdown.isVisible = false
//            countdownTitle.isVisible = false
//            activeBody.isVisible = false
//        }
//    }
//
//    private fun updateChatView(liveRoomState: LiveRoomViewModel.LiveRoomState) {
//        view.apply {
//            vm.liveChatSource?.let {
//                chatMessageCount.text = vm.messageList.count().toString()
//
//                chatButton.isVisible =
//                    liveRoomState.isValidStateForChat && vm.episode?.isChatEnabled == true && isChatEnabled
//
//                chatInputLayout.isVisible = isChatVisible
//                chatListView.isVisible = isChatVisible
//            }
//            footer.isVisible =
//                liveRoomState != LiveRoomViewModel.LiveRoomState.Finished && vm.liveChatSource != null
//        }
//    }
//
//    private fun setUpEndBody() {
//        vm.nextLiveStream?.apply {
//            view.nextStreamTitle.text = title?.uppercase(Locale.getDefault())
//            view.nextCoachImage.load(streamer?.profileImage?.medium)
//            val coachName = "${streamer?.firstName} ${streamer?.lastName}"
//            view.nextCoachName.text = getString(R.string.with_coach, coachName)
//
//            view.remindButton.setOnClickListener {
//                vm.toggleReminderFor(this)
//            }
//
//            setRemindButtonText()
//        }
//    }
//
//    private fun sendMessage(message: String) {
//        if (message.isNotBlank()) {
//            if (vm.user == null && vm.guestUsername == null) {
//                openUsernameDialog(message)
//            } else {
//                vm.sendMessage()
//                view.chatInputText.setText("")
//            }
//        }
//    }
//
//    private fun openUsernameDialog(message: String) {
//        requireContext().apply {
//            parentFragmentManager.openInputTextDialog(
//                title = getString(R.string.username),
//                description = getString(R.string.chat_username_dialog_description),
//                hint = getString(R.string.username),
//                positiveButtonText = getString(R.string.ok),
//                negativeButtonText = getString(R.string.cancel),
//                listener = object : TextInputDialog.TextInputDialogListener {
//                    override fun onClickPositiveButton(text: String) {
//                        vm.guestUsername = text
//                        sendMessage(message)
//                    }
//                }
//            )
//        }
//    }
//
//    private fun toggleChatVisibility(show: Boolean? = null) {
//        if (showProducts)
//            toggleProductListVisibility()
//
//        isChatVisible = show ?: !isChatVisible
//
//        view.apply {
//            chatMessageCount.isVisible = !isChatVisible
//            chatInputLayout.isVisible = isChatVisible
//            chatListView.isVisible = isChatVisible
//
//            updateProductButtonVisibility()
//
//            if (vm.liveRoomState.value?.isValidStateForBody == true && vm.episode?.isBroadcasting == false) {
//                activeBody.isVisible = !isChatVisible
//            }
//        }
//    }
//
//    private fun toggleProductListVisibility(show: Boolean? = null) {
//
//        showProducts = show ?: !showProducts
//
//        val productsToDisplay = if (showProducts)
//            productList
//        else
//            highlightedProductList
//
//        productAdapter.update(productsToDisplay)
//
//        view.apply {
//            productListView.isVisible =
//                (showProducts || highlightedProductList.isNotEmpty()) && isBroadcastingOrPlaying
//        }
//
//        if (isChatVisible)
//            toggleChatVisibility(!showProducts)
//    }
//
//    private fun updateProductButtonVisibility() {
//        view.apply {
//            productButton.isVisible = hasProducts && !isChatVisible && isBroadcastingOrPlaying
//        }
//    }
//
//    private fun setStreamTitle(liveState: LiveRoomViewModel.LiveRoomState) {
//        val title = if (liveState == LiveRoomViewModel.LiveRoomState.Finished) {
//            getString(R.string.end_stream_title)
//        } else {
//            vm.episode?.title
//        }
//
//        view.title.text = title?.uppercase(Locale.getDefault())
//    }

//    private fun setDescription(episode: Episode?) {
//        val string = if (episode?.status == EpisodeStatus.WaitingRoom)
//            episode.show?.waitingRoomDescription
//        else
//            episode?.description
//
//        view.description.text = string?.uppercase(Locale.getDefault())
//    }

    private fun initVideoPlayer(url: BroadcastUrl) {
        val video: String?
        var productTimeCodeList: List<ProductTimeCodes>? = null

        if (vm.isVideo) {
            video = vm.episode?.video?.videoURL
            productTimeCodeList = getProductTimeCodes()
        } else
            video = url.broadcastUrl

        video?.let {
            val videoFragment = VideoPlayerFragment.newInstance(
                video = it,
                isLive = true,
                timeCode = url.elapsedTime,
                productTimeCodes = productTimeCodeList
            ).apply {
                liveFragmentListener = this@LivePlayerFragment
            }
            parentFragmentManager.startFragment(videoFragment, view.videoPlayerContainer.id)
        }

    }

    private fun getProductTimeCodes(): List<ProductTimeCodes> {
        val productTimeCodes = listOf<ProductTimeCodes>()
        productList.forEach { product ->

            product.highlightTimingList?.map {
                ProductTimeCodes(
                    productId = product.id,
                    startTime = it.startTime.asMilliSeconds,
                    endTime = it.endTime.asMilliSeconds
                )
            }
        }
        return productTimeCodes
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
                vm.nextLiveStream?.isActive == true -> R.string.join_live
                vm.nextLiveStream?.hasReminder == true -> R.string.reminder_set
                else -> R.string.remind_me
            }
        )
    }

    override fun onLiveFinished() {
        vm.onLiveFinished()
    }

    override fun onLiveReady() {
        vm.onLiveReady()
        listener?.onLiveReady()
    }

    override fun onPlayerError(errorMessage: String?) {
        listener?.onPlayerError(errorMessage)
    }

    override fun onPlayerLoad() {
        listener?.onPlayerLoad()
    }

    override fun onProductTimeCodeReached(id: String, shouldShow: Boolean) {
        vm.changeProductHighLighting(id, shouldShow)
    }

    companion object {

        private const val LIVE_STREAM = "LIVE_STREAM"
        private const val NEXT_LIVE_STREAM = "NEXT_LIVE_STREAM"
        private const val IS_CHAT_ENABLED = "IS_CHAT_ENABLED"

        @JvmStatic
        fun newInstance(episode: Episode?, nextEpisode: Episode?, isChatEnabled: Boolean) =
            LivePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LIVE_STREAM, episode)
                    putParcelable(NEXT_LIVE_STREAM, nextEpisode)
                    putBoolean(IS_CHAT_ENABLED, isChatEnabled)
                }
            }
    }
}
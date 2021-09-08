package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pbj.sdk.common.ui.PlayerSettings
import com.pbj.sdk.common.ui.VideoPlayerView
import com.pbj.sdk.common.ui.VideoState
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.LivePlayerFinishedStateOverlay
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.LivePlayerInfo

@Composable
internal fun LivePlayerView(
    episode: Episode,
    nextEpisode: Episode?,
    streamUrl: BroadcastUrl?,
    isPlaying: Boolean,
    playerSettings: PlayerSettings,
    isChatEnabled: Boolean,
    chatText: String?,
    chatMessageList: List<ChatMessage>,
    onChatTextChange: (String) -> Unit,
    sendMessage: () -> Unit,
    countdownTime: String,
    productList: List<Product>,
    featuredProductList: List<Product>,
    onClickProduct: (Product) -> Unit,
    onClickBack: () -> Unit,
    onClickRemind: (Episode) -> Unit,
    onClickJoin: (Episode) -> Unit,
    onPlayerStateChange: (VideoState) -> Unit
) {
    var showOverlay by remember { mutableStateOf(true) }

    var showChat by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .background(Color.Black)
            .clickable {
                showOverlay = !showOverlay
            }) {
        if (!isPlaying) {
            val bgImageUrl = if (episode.isFinished)
                nextEpisode?.fullSizeImage
            else episode.fullSizeImage
            bgImageUrl?.let {
                BackgroundImage(it)
            }
        }

        when {
            (episode.isBroadcasting || episode.isFinished || isPlaying) && !streamUrl?.broadcastUrl.isNullOrBlank() -> {
                VideoPlayerView(
                    url = streamUrl?.broadcastUrl!!,
                    timeCode = streamUrl.elapsedTime,
                    settings = playerSettings,
                    soundEnabled = true,
                    onVideoPlayerStateChange = onPlayerStateChange
                )
            }
            episode.isFinished && !isPlaying -> {
                LivePlayerFinishedStateOverlay(
                    episode = episode,
                    countdownTime = countdownTime,
                    onClickBack = onClickBack,
                    onClickRemind = onClickRemind,
                    onClickJoin = onClickJoin
                )
            }
        }
        if (!episode.isFinished && showOverlay) {
            LivePlayerInfo(
                episode = episode,
                isPlaying = isPlaying,
                isChatEnabled = isChatEnabled,
                showChat = showChat,
                onClickChatButton = {
                    showChat = !showChat
                },
                chatText = chatText,
                chatMessages = chatMessageList,
                onChatTextChange = onChatTextChange,
                sendMessage = sendMessage,
                products = productList,
                featuredProducts = featuredProductList,
                onClickProduct = {
                    showOverlay = false
                    onClickProduct(it)
                },
                countdownTime = countdownTime,
                close = onClickBack
            )
        }
    }
}

@Preview
@Composable
private fun LivePlayerPreview() {
    LivePlayerView(
        episode = LivePreviewData.liveChatWaitingRoom,
        nextEpisode = LivePreviewData.liveChatBroadcastRoom,
        streamUrl = null,
        isPlaying = false,
        playerSettings = PlayerSettings(),
        isChatEnabled = true,
        chatText = "Hi dude",
        onChatTextChange = {},
        chatMessageList = listOf(),
        sendMessage = { },
        countdownTime = "10 45 29",
        productList = listOf(),
        featuredProductList = listOf(),
        onClickProduct = {},
        onClickBack = {},
        onClickJoin = {},
        onClickRemind = {},
        onPlayerStateChange = {}
    )
}


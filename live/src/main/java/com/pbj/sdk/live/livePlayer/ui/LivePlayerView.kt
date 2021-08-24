package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeStatus
import com.pbj.sdk.domain.live.model.fullSizeImage
import com.pbj.sdk.domain.live.model.isFinished
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.LivePlayerFinishedStateOverlay
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.LivePlayerInfo

@Composable
internal fun LivePlayerView(
    episode: Episode,
    nextEpisode: Episode?,
    isPlaying: Boolean,
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
    onClickJoin: (Episode) -> Unit
) {
    var showOverlay by remember { mutableStateOf(true) }

    Box(Modifier.clickable {
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

        Crossfade(episode.status) {
            when (it) {
                EpisodeStatus.Broadcasting -> {

                }
                EpisodeStatus.Finished -> {
                    if (!isPlaying) {
                        LivePlayerFinishedStateOverlay(
                            episode = episode,
                            countdownTime = countdownTime,
                            onClickBack = onClickBack,
                            onClickRemind = onClickRemind,
                            onClickJoin = onClickJoin
                        )
                    }
                }
            }
            if (!episode.isFinished) {
                LivePlayerInfo(
                    episode = episode,
                    isPlaying = isPlaying,
                    isChatEnabled = isChatEnabled,
                    chatText = chatText,
                    chatMessages = chatMessageList,
                    onChatTextChange = onChatTextChange,
                    sendMessage = sendMessage,
                    products = productList,
                    featuredProducts = featuredProductList,
                    onClickProduct = onClickProduct,
                    countdownTime = countdownTime,
                    close = onClickBack
                )
            }
        }
    }
}

@Preview
@Composable
private fun LivePlayerPreview() {
    LivePlayerView(
        episode = LivePreviewData.liveChatWaitingRoom,
        nextEpisode = LivePreviewData.liveChatBroadcastRoom,
        isPlaying = false,
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
        onClickRemind = {}
    )
}


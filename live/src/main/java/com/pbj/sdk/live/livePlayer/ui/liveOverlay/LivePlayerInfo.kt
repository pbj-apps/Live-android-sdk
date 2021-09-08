package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbj.sdk.common.ui.DoubleGradientView
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeStatus.Idle
import com.pbj.sdk.domain.live.model.EpisodeStatus.WaitingRoom
import com.pbj.sdk.domain.live.model.isBroadcasting
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.chat.ChatListView
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.product.ProductListView

@Composable
fun LivePlayerInfo(
    episode: Episode,
    isPlaying: Boolean,
    isChatEnabled: Boolean,
    showChat: Boolean,
    chatText: String?,
    chatMessages: List<ChatMessage>,
    onClickChatButton: () -> Unit,
    onChatTextChange: (String) -> Unit,
    sendMessage: () -> Unit,
    products: List<Product>,
    featuredProducts: List<Product>,
    onClickProduct: (Product) -> Unit,
    countdownTime: String,
    close: () -> Unit
) {
    val isLive = episode.isBroadcasting || isPlaying

    var showProducts by remember {
        mutableStateOf(false)
    }

    val canShowChat = rememberSaveable(
        isChatEnabled,
        episode.status == WaitingRoom || episode.isBroadcasting,
        isPlaying
    ) {
        isChatEnabled && (episode.status == WaitingRoom || episode.isBroadcasting || isPlaying)
    }

    val canShowProducts = rememberSaveable(episode.isBroadcasting, products.isNotEmpty()) {
        episode.isBroadcasting && products.isNotEmpty()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DoubleGradientView()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LivePlayerHeader(
                isLive = isLive,
                title = episode.title,
                close = { close() }
            )
            Spacer(Modifier.weight(1f, true))
            if (episode.status == Idle || (episode.status == WaitingRoom && !showChat)) {
                EpisodeInfo(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    episode = episode,
                    countdownTime = countdownTime
                )
            }
            if (canShowProducts) {
                val productsToDisplay = if (showProducts) products else featuredProducts
                ProductListView(
                    modifier = Modifier.padding(bottom = 16.dp),
                    productList = productsToDisplay,
                    onClickProduct = onClickProduct
                )
            }
            if (canShowChat && showChat) {
                ChatListView(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(250.dp),
                    chatMessages
                )
            }

            LivePlayerFooter(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                messageCount = chatMessages.count().toString(),
                textMessage = chatText,
                canShowChat = canShowChat,
                isChatShown = showChat,
                onClickChatButton = onClickChatButton,
                onChatTextChange = onChatTextChange,
                sendMessage = sendMessage,
                productCount = products.count().toString(),
                canShowProducts = canShowProducts,
                onClickProductButton = {
                    showProducts = !showProducts
                }
            )
        }
    }
}

@Preview
@Composable
private fun LivePlayerInfoPreview() {
    LivePlayerInfo(
        episode = LivePreviewData.liveChatWaitingRoom,
        isPlaying = false,
        isChatEnabled = true,
        showChat = false,
        onClickChatButton = {},
        chatMessages = listOf(),
        onChatTextChange = {},
        sendMessage = {},
        products = listOf(),
        featuredProducts = listOf(),
        onClickProduct = {},
        chatText = "Hello",
        countdownTime = "05  58  39"
    ) {}
}

@Preview
@Composable
private fun LivePlayerInfoChatPreview() {
    LivePlayerInfo(
        episode = LivePreviewData.liveChatBroadcastRoom,
        isPlaying = false,
        isChatEnabled = true,
        showChat = true,
        onClickChatButton = {},
        chatMessages = LivePreviewData.chatMessageList,
        onChatTextChange = {},
        sendMessage = {},
        products = LivePreviewData.productList,
        featuredProducts = LivePreviewData.productList,
        onClickProduct = {},
        chatText = "Hello",
        countdownTime = "05  58  39"
    ) {}
}

@Preview
@Composable
private fun LivePlayerInfoPlayingPreview() {
    LivePlayerInfo(
        episode = LivePreviewData.liveChatBroadcastRoom,
        isPlaying = true,
        isChatEnabled = true,
        showChat = false,
        onClickChatButton = {},
        chatMessages = listOf(),
        onChatTextChange = {},
        sendMessage = {},
        products = listOf(),
        featuredProducts = listOf(),
        onClickProduct = {},
        chatText = "Hello",
        countdownTime = "05  58  39"
    ) {}
}
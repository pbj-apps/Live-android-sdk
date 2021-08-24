package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.DoubleGradientView
import com.pbj.sdk.common.ui.TextFieldDialog
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
    chatText: String?,
    chatMessages: List<ChatMessage>,
    onChatTextChange: (String) -> Unit,
    sendMessage: () -> Unit,
    products: List<Product>,
    featuredProducts: List<Product>,
    onClickProduct: (Product) -> Unit,
    countdownTime: String,
    showsUsernameAlert: Boolean = false,
    close: () -> Unit
) {
    val isLive = episode.isBroadcasting || isPlaying

    var showProducts by remember {
        mutableStateOf(false)
    }

    var showChat by remember {
        mutableStateOf(false)
    }

    val canShowChat = remember {
        isChatEnabled && (episode.status == WaitingRoom || episode.isBroadcasting)
    }

    val canShowProducts = remember {
        episode.isBroadcasting && products.isNotEmpty()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DoubleGradientView()
        Column(
            modifier = Modifier.fillMaxSize()
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
            if (!showProducts && canShowProducts) {
                ProductListView(productList = featuredProducts, onClickProduct = onClickProduct)
            }
            if (showProducts && canShowProducts) {
                ProductListView(productList = products, onClickProduct = onClickProduct)
            }
            if (canShowChat && showChat) {
                ChatListView(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp)
                        .padding(top = 230.dp),
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
                onClickChatButton = {
                    showChat = !showChat
                },
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

    if (showsUsernameAlert) {
        TextFieldDialog(
            title = stringResource(R.string.username),
            description = stringResource(R.string.chat_username_dialog_description),
            confirmButtonText = stringResource(R.string.ok),
            dismissButtonText = stringResource(R.string.cancel),
            onConfirm = {

            },
            onDismiss = {

            }
        )
    }
}

@Preview
@Composable
private fun LivePlayerInfoPreview() {
    LivePlayerInfo(
        episode = LivePreviewData.liveChatWaitingRoom,
        isPlaying = false,
        isChatEnabled = true,
        chatMessages = listOf(),
        onChatTextChange = {},
        sendMessage = {},
        products = listOf(),
        featuredProducts = listOf(),
        onClickProduct = {},
        chatText = "Hello",
        countdownTime = "05  58  39",
        showsUsernameAlert = false
    ) {}
}

@Preview
@Composable
private fun LivePlayerInfoPlayingPreview() {
    LivePlayerInfo(
        episode = LivePreviewData.liveChatBroadcastRoom,
        isPlaying = true,
        isChatEnabled = true,
        chatMessages = listOf(),
        onChatTextChange = {},
        sendMessage = {},
        products = listOf(),
        featuredProducts = listOf(),
        onClickProduct = {},
        chatText = "Hello",
        countdownTime = "05  58  39",
        showsUsernameAlert = false
    ) {}
}
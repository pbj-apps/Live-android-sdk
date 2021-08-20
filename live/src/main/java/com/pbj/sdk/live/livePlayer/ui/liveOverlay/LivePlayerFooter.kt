package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.chat.ChatButton
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.chat.ChatInput
import com.pbj.sdk.live.livePlayer.ui.liveOverlay.product.ProductButton

@Composable
fun LivePlayerFooter(
    modifier: Modifier = Modifier,
    messageCount: String,
    textMessage: String?,
    canShowChat: Boolean,
    isChatShown: Boolean,
    onClickChatButton: () -> Unit,
    sendMessage: () -> Unit,
    productCount: String,
    canShowProducts: Boolean,
    onClickProductButton: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (canShowChat) {
            ChatButton(messageCount = messageCount, onClickChatButton = onClickChatButton)
        }
        Spacer(modifier = Modifier.width(16.dp))
        if (canShowChat && isChatShown) {
            ChatInput(
                textMessage = textMessage,
                onChatTextChange = { },
                send = sendMessage
            )
        } else if (canShowProducts) {
            ProductButton(productCount = productCount, onClick = onClickProductButton)
        }
    }
}

@Preview
@Composable
private fun LivePlayerFooterPreview() {
    LivePlayerFooter(
        messageCount = "34",
        textMessage = null,
        canShowChat = true,
        isChatShown = true,
        onClickChatButton = {},
        sendMessage = {},
        productCount = "14",
        canShowProducts = true
    ) {}
}

@Preview
@Composable
private fun LivePlayerFooterChatNotShownPreview() {
    LivePlayerFooter(
        messageCount = "34",
        textMessage = null,
        canShowChat = true,
        isChatShown = false,
        onClickChatButton = {},
        sendMessage = {},
        productCount = "14",
        canShowProducts = true
    ) {}
}



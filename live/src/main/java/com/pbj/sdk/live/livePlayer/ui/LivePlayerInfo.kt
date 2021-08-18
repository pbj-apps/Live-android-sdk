package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pbj.sdk.common.ui.DoubleGradientView
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product

@Composable
internal fun LivePlayerInfo(
    episode: Episode,
    isChatEnabled: Boolean,
    isChatShown: Boolean = false,
    chatMessages: List<ChatMessage>,
    fetchMessages: () -> Void,
    sendMessage: (String, String?) -> Void,
    products: List<Product>,
    featuredProducts: List<Product>,
    isAllCaps: Boolean,
    isInGuestMode: Boolean,
    chatUsername: String?,
    chatText: String = "",
    showsUsernameAlert: Boolean = false,
    close: (() -> Void)?
) {
    Box {}
    DoubleGradientView()
}
}

@Preview
@Composable
internal fun LivePlayerInfo() {

}
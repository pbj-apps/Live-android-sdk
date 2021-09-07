package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.LiveRoomViewModel

@Composable
internal fun LivePlayerScreen(
    vm: LiveRoomViewModel = viewModel(),
    isChatEnabled: Boolean,
    onClickProduct: (Product) -> Unit,
    onClickJoin: (Episode) -> Unit,
    onClickBack: () -> Unit
) {
    vm.apply {
        episode?.let {
            LivePlayerView(
                episode = it,
                nextEpisode = nextLiveStream,
                streamUrl = streamUrl,
                isPlaying = isPlaying,
                playerSettings = playerSettings,
                isChatEnabled = isChatEnabled,
                chatText = chatText,
                onChatTextChange = ::onChatTextChange,
                chatMessageList = messageList,
                sendMessage = ::sendMessage,
                countdownTime = remainingTime,
                productList = productList,
                featuredProductList = highlightedProductList,
                onClickProduct = onClickProduct,
                onClickBack = onClickBack,
                onClickJoin = onClickJoin,
                onClickRemind = ::toggleReminderFor,
                onPlayerStateChange = ::onPlayerStateChange
            )
        }
    }
}

@Composable
fun BackgroundImage(url: String) {
    Image(
        painter = rememberImagePainter(
            url,
            builder = {
                crossfade(true)
            }),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.CenterStart,
        contentScale = ContentScale.Crop,
    )
}
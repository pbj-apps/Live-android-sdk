package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.TextFieldDialog
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.LiveRoomViewModel

@Composable
internal fun LivePlayerScreen(
    vm: LiveRoomViewModel,
    isChatEnabled: Boolean,
    onClickProduct: (Product) -> Unit,
    onClickJoin: (Episode) -> Unit,
    onClickBack: () -> Unit
) {
    var showUsernameDialog by remember {
        mutableStateOf(false)
    }

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
                sendMessage = {
                    if (vm.user == null && vm.guestUsername.isNullOrBlank())
                        showUsernameDialog = true
                    else sendMessage()
                },
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

        if (showUsernameDialog) {
            TextFieldDialog(
                title = stringResource(R.string.username),
                hint = stringResource(R.string.username),
                description = stringResource(R.string.chat_username_dialog_description),
                confirmButtonText = stringResource(R.string.ok),
                dismissButtonText = stringResource(R.string.cancel),
                onConfirm = {
                    if (it.isNotBlank()) {
                        vm.guestUsername = it
                        sendMessage()
                        showUsernameDialog = false
                    }
                },
                onDismiss = {
                    showUsernameDialog = false
                }
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
package com.pbj.sdk.live.livePlayer.ui.liveInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.DoubleGradientView
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.isBroadcasting
import com.pbj.sdk.domain.product.model.Product

@Composable
fun LivePlayerInfo(
    episode: Episode,
    isPlaying: Boolean,
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
    countdownTime: String,
    showsUsernameAlert: Boolean = false,
    close: () -> Void
) {
    val isLive = episode.isBroadcasting || isPlaying

    Box(modifier = Modifier.fillMaxSize()) {
        DoubleGradientView()
        Column {
            LivePlayerHeader(
                isLive = isLive,
                title = episode.title,
                close = { close() }
            )
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.next_up),
                    fontSize = 18.sp,
                    color = Color.White
                )
                episode.title?.let {
                    Text(
                        text = it,
                        fontSize = 39.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 60.dp)
                    )
                }
                episode.streamer?.apply {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                profileImage,
                                builder = {
                                    crossfade(true)
                                }),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(30.dp)
                                .padding(end = 8.dp)
                        )
                        Text(text = "With $firstName $lastName")
                    }

                    Text(
                        text = countdownTime,
                        modifier = Modifier.padding(top = 24.dp),
                        fontSize = 45.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LivePlayerInfo() {

}
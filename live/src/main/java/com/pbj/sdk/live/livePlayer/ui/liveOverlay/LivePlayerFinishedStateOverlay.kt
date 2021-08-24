package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.BottomGradient
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData
import com.pbj.sdk.live.livePlayer.ui.RemindButton

@Composable
fun LivePlayerFinishedStateOverlay(
    episode: Episode,
    countdownTime: String,
    onClickBack: () -> Unit,
    onClickRemind: (Episode) -> Unit,
    onClickJoin: (Episode) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BottomGradient(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxSize())
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    "back button",
                    tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onClickBack()
                        }
                        .size(20.dp)
                )

                Text(
                    text = stringResource(R.string.end_stream_title),
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
//                        .weight(1f, true)
                )

                Spacer(
                    modifier = Modifier
                        .size(15.dp)
//                        .padding(end = 16.dp)
                )
            }

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

            RemindButton(
                episode = episode,
                onClickRemind = onClickRemind,
                onClickJoin = onClickJoin
            )
        }
    }
}

@Preview
@Composable
private fun LivePlayerFinishedStateOverlayPreview() {
    LivePlayerFinishedStateOverlay(
        episode = LivePreviewData.liveChatWaitingRoom,
        countdownTime = "48  45  20",
        onClickBack = {},
        onClickRemind = {}
    ) {}
}
package com.pbj.sdk.live.sdkLivePlayer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.ClickableIcon
import com.pbj.sdk.domain.live.model.Show
import com.pbj.sdk.domain.vod.model.previewImage
import com.pbj.sdk.live.livePlayer.ui.BackgroundImage
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData

@Composable
fun SdkEpisodeEndView(show: Show?, close: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        show?.apply {
            previewAsset?.previewImage?.let {
                BackgroundImage(it)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = stringResource(R.string.end_stream_title),
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .weight(1f, true),
                textAlign = TextAlign.Center
            )
        }

        ClickableIcon(drawable = R.drawable.ic_cross) { close() }
    }
}

@Preview
@Composable
private fun SdkEpisodeEndPreview() {
    SdkEpisodeEndView(LivePreviewData.liveChatFinishedRoom.show) {}
}
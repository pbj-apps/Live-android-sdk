package com.pbj.sdk.live.livePlayer.ui.liveInfo

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R

@Composable
fun LivePlayerHeader(
    modifier: Modifier = Modifier,
    isLive: Boolean,
    title: String?,
    close: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            painter = painterResource(
                if (isLive) R.drawable.ic_live_on else R.drawable.ic_up_next
            ),
            null,
            modifier = Modifier
                .padding(16.dp)
        )

        Text(
            text = stringResource(R.string.end_stream_title),
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Icon(
            painter = painterResource(R.drawable.ic_cross),
            null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    close()
                }
                .size(15.dp)
                .padding(16.dp)
        )
    }
}

@Preview(name = "Live")
@Composable
private fun LivePlayerHeaderLivePreview() {
    LivePlayerHeader(
        isLive = true,
        title = "Workout of the day"
    ) {}
}

@Preview(name = "Not Live")
@Composable
private fun LivePlayerHeaderPreview() {
    LivePlayerHeader(
        isLive = false,
        title = "Workout of the day"
    ) {}
}
package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
//        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(
                if (isLive) R.drawable.ic_live_on else R.drawable.ic_up_next
            ),
            null,
            modifier = Modifier
                .padding(16.dp)
        )

        title?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } ?: Spacer(modifier = Modifier.weight(1f, true))

        Icon(
            painter = painterResource(R.drawable.ic_cross),
            null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    close()
                }
                .padding(16.dp)
                .size(20.dp)
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

@Preview(name = "No Title")
@Composable
private fun LivePlayerHeaderNoTitlePreview() {
    LivePlayerHeader(
        isLive = false,
        title = null
    ) {}
}
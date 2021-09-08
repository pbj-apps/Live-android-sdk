package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.CloseButton

@Composable
fun LivePlayerHeader(
    modifier: Modifier = Modifier,
    isLive: Boolean,
    title: String?,
    close: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(
                if (isLive) R.drawable.ic_live_on else R.drawable.ic_up_next
            ),
            null,
            modifier = Modifier.padding(16.dp)
        )

        title?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .weight(1f, true),
                textAlign = TextAlign.Center
            )
        } ?: Spacer(modifier = Modifier.weight(1f, true))

        CloseButton { close() }
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
        title = "Workout of the day and any kind of training improving your body performances"
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
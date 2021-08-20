package com.pbj.sdk.live.livePlayer.ui.liveOverlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.descriptionToDisplay
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData

@Composable
fun EpisodeInfo(modifier: Modifier = Modifier, episode: Episode, countdownTime: String) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Bottom
    ) {

        episode.descriptionToDisplay?.let {
            Text(
                modifier = Modifier.padding(bottom = 55.dp),
                text = it,
                fontSize = 50.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }

        Text(
            text = stringResource(R.string.countdown_title),
            fontSize = 14.sp,
            color = Color.White
        )

        Text(
            modifier = Modifier.padding(bottom = 50.dp),
            text = countdownTime,
            fontSize = 45.sp,
            color = Color.White
        )
    }
}

@Preview(name = "Idle Episode Info")
@Composable
private fun EpisodeInfoIdlePreview() {
    EpisodeInfo(
        episode = LivePreviewData.liveChatIdle,
        countdownTime = "00  37  54"
    )
}

@Preview
@Preview(name = "Waiting Room Episode Info")
@Composable
private fun EpisodeInfoWaitingRoomPreview() {
    EpisodeInfo(
        episode = LivePreviewData.liveChatWaitingRoom,
        countdownTime = "00  37  54"
    )
}
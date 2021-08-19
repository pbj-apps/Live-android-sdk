package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeStatus

@Composable
fun RemindButton(
    episode: Episode,
    modifier: Modifier = Modifier,
    onClickRemind: (Episode) -> Unit,
    onClickJoin: (Episode) -> Unit
) {
    val isLive = episode.status == EpisodeStatus.Broadcasting
    val text = when {
        isLive -> R.string.join_live
        episode.hasReminder -> R.string.reminder_set
        else -> R.string.remind_me
    }

    Button(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.liveButtonBackground),
            contentColor = colorResource(R.color.liveButtonForeground),
            disabledBackgroundColor = backgroundColor.copy(alpha = ContentAlpha.disabled)
        ),
        onClick = {
            if (isLive)
                onClickJoin.invoke(episode)
            else
                onClickRemind.invoke(episode)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = stringResource(text),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            if (!isLive) {
                Icon(
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
//                    tint = textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun RemindButtonRemindingPreview() {
    RemindButton(LivePreviewData.liveChatBroadcastRoom, Modifier, {}) {}
}

@Preview
@Composable
fun RemindButtonRemindPreview() {
    RemindButton(LivePreviewData.liveChatFinishedRoom, Modifier, {}) {}
}
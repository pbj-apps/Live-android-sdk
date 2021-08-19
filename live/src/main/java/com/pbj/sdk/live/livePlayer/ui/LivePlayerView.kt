package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.exoplayer2.ExoPlayer
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeStatus
import com.pbj.sdk.domain.live.model.fullSizeImage
import com.pbj.sdk.domain.live.model.isFinished

@Composable
internal fun LivePlayerView(
    episode: Episode,
    nextEpisode: Episode?,
    isPlaying: Boolean,
    player: ExoPlayer,
    countdownTime: String,
    onClickBack: () -> Unit,
    onClickRemind: (Episode) -> Unit,
    onClickJoin: (Episode) -> Unit
) {
    var showOverlay by remember { mutableStateOf(true) }

    Box(Modifier.clickable {
        showOverlay = !showOverlay
    }) {
        if (!isPlaying) {
            val bgImageUrl = if (episode.isFinished)
                nextEpisode?.fullSizeImage
            else episode.fullSizeImage
            bgImageUrl?.let {
                BackgroundImage(it)
            }
        }

        Crossfade(episode.status) {
            when (it) {
                EpisodeStatus.Broadcasting -> {

                }
                EpisodeStatus.Finished -> {
                    if (!isPlaying) {
                        LivePlayerFinishedStateOverlay(
                            episode = episode,
                            countdownTime = countdownTime,
                            onClickBack = onClickBack,
                            onClickRemind = onClickRemind,
                            onClickJoin = onClickJoin
                        )
                    }
                }
            }
            if (!episode.isFinished) {
//                LivePlayerInfo(
//
//                )
            }
        }
    }
}


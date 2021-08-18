package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.live.livePlayer.LiveRoomViewModel.LiveRoomState

@Composable
internal fun LivePlayerView(episode: Episode, screenState: LiveRoomState) {

    val showOverlay by remember { mutableStateOf(true) }

    Box(Modifier.clickable {

    }) {
        episode.image?.fullSize?.let {
            BackgroundImage(it)
        }
        Crossfade(screenState) {
            when (it) {
                LiveRoomState.Idle -> {
                }
            }
        }
    }
}


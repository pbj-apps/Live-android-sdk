package com.pbj.sdk.live.livePlayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.pbj.sdk.live.livePlayer.LiveRoomViewModel

@Composable
internal fun LivePlayerScreen(
    vm: LiveRoomViewModel = viewModel()
) {
//    Crossfade(vm.liveRoomState) {
//        when(it) {
//
//        }
//    }
}

@Composable
fun BackgroundImage(url: String) {
    Image(
        painter = rememberImagePainter(
            url,
            builder = {
                crossfade(true)
            }),
        contentDescription = null,
        modifier = Modifier
            .width(160.dp)
            .fillMaxHeight()
            .padding(end = 16.dp)
            .clip(RoundedCornerShape(10.dp)),
        alignment = Alignment.CenterStart,
        contentScale = ContentScale.Crop,
    )
}
package com.pbj.sdk.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pbj.sdk.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    size: Int = 20,
    clickAreaSize: Int = 16,
    clickAreaShape: Shape = CircleShape,
    close: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.ic_cross),
        "Close",
        tint = Color.White,
        modifier = modifier
            .clip(clickAreaShape)
            .clickable {
                close()
            }
            .padding(clickAreaSize.dp)
            .size(size.dp)
    )
}
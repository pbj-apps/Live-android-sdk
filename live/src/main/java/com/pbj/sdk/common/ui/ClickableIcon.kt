package com.pbj.sdk.common.ui

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier,
    description: String? = null,
    size: Dp = 20.dp,
    tint: Color = Color.White,
    clickAreaSize: Dp = 16.dp,
    clickAreaShape: Shape = CircleShape,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(drawable),
        description,
        tint = tint,
        modifier = modifier
            .clip(clickAreaShape)
            .clickable {
                onClick()
            }
            .padding(clickAreaSize)
            .size(size)
    )
}
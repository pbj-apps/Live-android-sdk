package com.pbj.sdk.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GradientView(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
    startY: Float? = 0.0f, endY: Float? = null
) {
    Box(
        modifier = modifier.verticalGradientBackground(colors, startY = startY, endY = endY)
    )
}

@Composable
fun BottomGradient(modifier: Modifier = Modifier) {
    GradientView(
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun BottomGradientPreview() {
    BottomGradient()
}

fun Modifier.verticalGradientBackground(
    colors: List<Color>,
    startY: Float? = 0.0f,
    endY: Float? = null,
) = drawWithCache {
    val gradient = Brush
        .verticalGradient(startY = startY ?: 0.0f, endY = endY ?: size.height, colors = colors)
    onDrawBehind {
        drawRect(brush = gradient)
    }
}
package com.pbj.sdk.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DoubleGradientView() {
    Column(modifier = Modifier.fillMaxSize()) {
        GradientView(
            colors = listOf(Color.Black.copy(0.6f), Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        GradientView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DoubleGradientViewPreview() {
    DoubleGradientView()
}
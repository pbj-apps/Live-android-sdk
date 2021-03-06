package com.pbj.sdk.live.sdkLivePlayer

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R
import com.pbj.sdk.common.ui.ClickableIcon

@Composable
internal fun SdkInformationView(
    @StringRes title: Int,
    @StringRes description: Int,
    close: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        ClickableIcon(drawable = R.drawable.ic_cross, modifier = Modifier.align(TopEnd)) { close() }

        Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(title),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(description),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun SdkNoEpisodePreview() {
    SdkNoEpisodeView {}
}


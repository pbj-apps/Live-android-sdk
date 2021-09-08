package com.pbj.sdk.live.sdkLivePlayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.common.ui.CloseButton
import com.pbj.sdk.common.ui.DoubleGradientView
import com.pbj.sdk.domain.live.model.Show
import com.pbj.sdk.domain.vod.model.previewImage
import com.pbj.sdk.live.livePlayer.ui.BackgroundImage
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData

@Composable
fun SdkShowDetailsView(show: Show, close: () -> Unit) {
    show.apply {
        Box(modifier = Modifier.background(Color.Black)) {
            show.previewAsset?.previewImage?.let {
                BackgroundImage(it)
            }
            DoubleGradientView()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f, true)
                        )
                    } ?: Spacer(modifier = Modifier.weight(1f, true))

                    CloseButton(modifier = Modifier.align(Alignment.Top)) { close() }
                }

                description?.let {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 73.dp)
                            .padding(horizontal = 16.dp),
                        text = it,
                        fontSize = 50.sp,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SdkShowDetailsPreview() {
    SdkShowDetailsView(
        LivePreviewData.show.copy(
            title = "Best live to get to know better the proudcts you need before you even think about buying them",
            description = "Best live to get to know better the proudcts you need before you even think about buying them"
        )
    ) {}
}
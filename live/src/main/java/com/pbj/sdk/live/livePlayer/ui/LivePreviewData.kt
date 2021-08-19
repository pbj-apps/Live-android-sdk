package com.pbj.sdk.live.livePlayer.ui

import com.pbj.sdk.domain.live.model.ChatMode
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeStatus
import com.pbj.sdk.domain.live.model.Show
import com.pbj.sdk.domain.vod.model.Asset
import com.pbj.sdk.domain.vod.model.Image

internal object LivePreviewData {

    private const val URL = "www.google.com"

    private val image = Image(URL, URL, URL)

    private val show = Show(
        "d",
        "Show Title",
        "Show Description",
        "Waiting Room Description",
        duration = 3600,
        startDate = null,
        endDate = null,
        instructors = listOf(),
        previewAsset = Asset(
            "asf", "image", URL, Image(
                URL, URL, URL
            ),
            null,
            "idle"
        )
    )

    val liveChatIdle = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Idle, null
    )

    val liveChatWaitingRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.WaitingRoom, null
    )

    val liveChatBroadcastRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Broadcasting, null
    )

    val liveChatFinishedRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Finished, null
    )
}
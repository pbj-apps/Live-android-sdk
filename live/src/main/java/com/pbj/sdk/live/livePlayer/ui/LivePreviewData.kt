package com.pbj.sdk.live.livePlayer.ui

import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.vod.model.Asset
import com.pbj.sdk.domain.vod.model.Image
import com.pbj.sdk.domain.vod.model.ProfileImage

internal object LivePreviewData {

    private const val URL = "www.google.com"

    private val image = Image(URL, URL, URL)

    val show = Show(
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

    val streamer = Streamer(
        id = "90",
        firstName = "George",
        lastName = "Harrison",
        profileImage = ProfileImage("asd6", URL, URL, URL)
    )

    val liveChatIdle = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Idle, streamer
    )

    val liveChatWaitingRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.WaitingRoom, streamer
    )

    val liveChatBroadcastRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Broadcasting, streamer
    )

    val liveChatFinishedRoom = Episode(
        "", "Title of the live stream",
        "Description of the livestream", ChatMode.ENABLED, 9,
        image, null, null, show, EpisodeStatus.Finished, streamer
    )

    val chatMessageList = listOf(
        ChatMessage("Hey guys what's up?", "Bobby"),
        ChatMessage("Good how are you mate?", "Syd"),
        ChatMessage(
            "Pretty well but I wish I had someone to talk to, I really want to write a novel about my boring life but I'm sure nobody would care and that's a pity cause I have so much to not tell!",
            "Bobby"
        ),
        ChatMessage("Well don't write anything, it's better for everybody dude", "Odin"),
        ChatMessage("Hey", "Paul"),
        ChatMessage("Hola", "John"),
        ChatMessage("Ciao Mundo", "Paulo"),
        ChatMessage("Nice", "Roger"),
        ChatMessage("Hey guys what's up?", "Bobby"),
        ChatMessage("Good how are you mate?", "Syd"),
        ChatMessage(
            "Pretty well but I wish I had someone to talk to, I really want to write a novel about my boring life but I'm sure nobody would care and that's a pity cause I have so much to not tell!",
            "Bobby"
        ),
        ChatMessage("Well don't write anything, it's better for everybody dude", "Odin"),
        ChatMessage("Hey", "Paul"),
        ChatMessage("Hola", "John"),
        ChatMessage("Ciao Mundo", "Paulo"),
        ChatMessage("Nice", "Roger")
    )

    val productList = listOf(
        Product("687"),
        Product("687"),
        Product("6523587"),
        Product("sdgsd4"),
        Product("sdf"),
        Product("243")
    )
}
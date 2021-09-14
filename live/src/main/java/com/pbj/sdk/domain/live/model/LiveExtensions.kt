package com.pbj.sdk.domain.live.model

import com.pbj.sdk.concreteImplementation.authentication.model.GuestAuthResponse
import com.pbj.sdk.concreteImplementation.live.model.JSONGuestAuthResponse
import java.time.OffsetDateTime

val Episode.isPast: Boolean
    get() = endDate?.isBefore(OffsetDateTime.now()) ?: false

val Episode.isNotPast: Boolean
    get() = !isPast

val EpisodeStatus.isActive: Boolean
    get() = this == EpisodeStatus.WaitingRoom || this == EpisodeStatus.Broadcasting

val EpisodeStatus.isBeforeBroadcast: Boolean
    get() = this == EpisodeStatus.Idle || this == EpisodeStatus.WaitingRoom

val Episode.isBeforeBroadcast: Boolean
    get() = status.isBeforeBroadcast

val Episode.isActive: Boolean
    get() = status.isActive

val EpisodeStatus.isBroadcasting: Boolean
    get() = this == EpisodeStatus.Broadcasting

val Episode.isBroadcasting: Boolean
    get() = status.isBroadcasting

val EpisodeStatus.isFinished: Boolean
    get() = this == EpisodeStatus.Finished

val Episode.isFinished: Boolean
    get() = status.isFinished

val Episode.isChatEnabled: Boolean
    get() = chatMode == ChatMode.ENABLED || chatMode == ChatMode.QNA

val Episode.showId: String?
    get() = show?.id

val Episode.fullSizeImage: String?
    get() = image?.fullSize

val Episode.waitingRoomDescription: String?
    get() = show?.waitingRoomDescription

val Episode.descriptionToDisplay: String?
    get() = when (status) {
        EpisodeStatus.Idle -> description
        EpisodeStatus.WaitingRoom -> waitingRoomDescription
        EpisodeStatus.Broadcasting -> "Streaming"
        EpisodeStatus.Finished -> "Stream finished"
    }

val JSONGuestAuthResponse.asModel: GuestAuthResponse
    get() = GuestAuthResponse(auth_token)

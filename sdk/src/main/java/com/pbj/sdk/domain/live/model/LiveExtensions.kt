package com.pbj.sdk.domain.live.model

import com.pbj.sdk.concreteImplementation.authentication.model.GuestAuthResponse
import com.pbj.sdk.concreteImplementation.live.model.JSONGuestAuthResponse
import java.time.OffsetDateTime

val Episode.fullSizeImage: String?
    get() = show?.previewAsset?.image?.fullSize

val Episode.isPast: Boolean
    get() = endDate?.isBefore(OffsetDateTime.now()) ?: false

val Episode.isNotPast: Boolean
    get() = !isPast

val EpisodeStatus.isActive: Boolean
    get() = this == EpisodeStatus.WAITING_ROOM || this == EpisodeStatus.BROADCASTING

val EpisodeStatus.isBeforeBroadcast: Boolean
    get() = this == EpisodeStatus.IDLE || this == EpisodeStatus.WAITING_ROOM

val Episode.isBeforeBroadcast: Boolean
    get() = status.isBeforeBroadcast

val Episode.isActive: Boolean
    get() = status.isActive

val EpisodeStatus.isBroadcasting: Boolean
    get() = this == EpisodeStatus.BROADCASTING

val Episode.isBroadcasting: Boolean
    get() = status.isBroadcasting

val EpisodeStatus.isFinished: Boolean
    get() = this == EpisodeStatus.FINISHED

val Episode.isFinished: Boolean
    get() = status.isFinished

val Episode.isChatEnabled: Boolean
    get() = chatMode == ChatMode.ENABLED || chatMode == ChatMode.QNA

val Episode.showId: String?
    get() = show?.id

val JSONGuestAuthResponse.asModel: GuestAuthResponse
    get() = GuestAuthResponse(auth_token)

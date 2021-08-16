package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.concreteImplementation.live.model.JsonBroadcastUrl
import com.pbj.sdk.concreteImplementation.live.model.JsonEpisodeResponse
import com.pbj.sdk.concreteImplementation.live.model.JsonShow
import com.pbj.sdk.concreteImplementation.live.model.LiveNotificationSubscription
import retrofit2.Response
import retrofit2.http.*

internal interface LiveApi {

    @GET("live-streams")
    suspend fun fetchLiveStreams(): Response<JsonEpisodeResponse>

    @GET("v1/episodes")
    suspend fun fetchLiveStreamsSchedule(
        @Query("starting_at") startDate: String?,
        @Query("days_ahead") daysAhead: Int?,
        @Query("per_page") size: Int?
    ): Response<JsonEpisodeResponse>

    @GET
    suspend fun fetchNextEpisodePage(@Url url: String): Response<JsonEpisodeResponse>

    @GET("episodes/current")
    suspend fun fetchCurrentLiveStream(): Response<JsonEpisodeResponse>

    @GET("episodes/current")
    suspend fun fetchCurrentLiveStream(@Query("show_id") showId: String): Response<JsonEpisodeResponse?>

    @GET("shows/{id}/public")
    suspend fun fetchShowPublic(@Path("id") showId: String): Response<JsonShow>

    @GET("live-streams/{id}/watch")
    suspend fun fetchBroadcastUrl(@Path("id") id: String): Response<JsonBroadcastUrl>

    @POST("push-notifications/subscribe")
    suspend fun subscribeToNotification(@Body request: LiveNotificationSubscription): Response<Any>

    @POST("push-notifications/unsubscribe")
    suspend fun unsubscribeFromNotifications(@Body request: LiveNotificationSubscription): Response<Any>
}
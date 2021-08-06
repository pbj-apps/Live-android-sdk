package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.concreteImplementation.live.model.*
import retrofit2.Response
import retrofit2.http.*

internal interface LiveApi {

    @GET("live-streams")
    suspend fun fetchLiveStreams(): Response<JsonEpisodeResponse>

    @GET("episodes/schedule")
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

    @GET("notifications/subscriptions")
    suspend fun fetchNotificationSubscriptions(): Response<JsonLiveNotificationResponse>

    @POST("push-notifications/subscribe")
    suspend fun subscribeToNotification(@Body request: LiveNotificationSubscription): Response<Any>

    @POST("push-notifications/unsubscribe")
    suspend fun unsubscribeFromNotifications(@Body request: LiveNotificationSubscription): Response<Any>
}
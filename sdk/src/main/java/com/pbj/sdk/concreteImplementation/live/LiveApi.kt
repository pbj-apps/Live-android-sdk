package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.concreteImplementation.live.model.*
import retrofit2.Response
import retrofit2.http.*

internal interface LiveApi {

    @GET("live-streams")
    suspend fun fetchLiveStreams(): Response<JsonEpisodeResponse>

    @GET("live-streams/schedule")
    suspend fun fetchLiveStreamsSchedule(@Query("starting_at") startDate: String): Response<JsonEpisodeResponse>

    @GET("live-streams/schedule")
    suspend fun fetchLiveStreamsSchedule(@Query("days_ahead") daysAhead: Int): Response<JsonEpisodeResponse>

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

    @POST("notifications/subscriptions")
    suspend fun subscribeToNotification(@Body request: LiveNotificationSubscription): Response<Any>

    @HTTP(method = "DELETE", path = "notifications/subscriptions", hasBody = true)
    suspend fun unsubscribeFromNotifications(@Body request: LiveNotificationSubscription): Response<Any>
}
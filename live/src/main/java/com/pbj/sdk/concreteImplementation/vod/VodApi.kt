package com.pbj.sdk.concreteImplementation.vod

import com.pbj.sdk.concreteImplementation.vod.model.JsonVodCategoriesPage
import com.pbj.sdk.concreteImplementation.vod.model.JsonVodPlaylist
import com.pbj.sdk.concreteImplementation.vod.model.JsonVodVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface VodApi {

    @GET("vod/categories")
    suspend fun getCategories(@Query("items_per_category") itemsPerCategory: Int): Response<JsonVodCategoriesPage>

    @GET
    suspend fun fetchNextVodPage(@Url url: String): Response<JsonVodCategoriesPage>

    @GET("vod/playlists/{id}")
    suspend fun getPlaylist(@Path("id") id: String?): Response<JsonVodPlaylist>

    @GET("vod/videos/{id}")
    suspend fun getVideo(@Path("id") id: String?): Response<JsonVodVideo>
}
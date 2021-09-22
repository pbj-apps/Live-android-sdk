package com.pbj.sdk.di

import android.app.Application
import android.content.Context
import com.pbj.sdk.concreteImplementation.authentication.GuestApi
import com.pbj.sdk.concreteImplementation.authentication.UserApi
import com.pbj.sdk.concreteImplementation.generic.FlowStreamAdapter
import com.pbj.sdk.concreteImplementation.interceptors.AcceptInterceptor
import com.pbj.sdk.concreteImplementation.interceptors.ApiKeyInterceptor
import com.pbj.sdk.concreteImplementation.interceptors.AuthorizationInterceptor
import com.pbj.sdk.concreteImplementation.interceptors.TimeZoneInterceptor
import com.pbj.sdk.concreteImplementation.live.LiveApi
import com.pbj.sdk.concreteImplementation.live.LiveWebSocketApi
import com.pbj.sdk.concreteImplementation.organization.OrganizationApi
import com.pbj.sdk.concreteImplementation.product.ProductApi
import com.pbj.sdk.concreteImplementation.product.ProductWebSocketApi
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.concreteImplementation.vod.VodApi
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.core.domain
import com.pbj.sdk.core.isDebug
import com.pbj.sdk.parser.DateParser
import com.pbj.sdk.parser.DateTimeParser
import com.pbj.sdk.parser.UrlParser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.OkHttpClientWebSocketConnectionEstablisher
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import com.tinder.scarlet.websocket.okhttp.request.RequestFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

internal object DataModule {

    private lateinit var apiEnvironment: ApiEnvironment
    private lateinit var apiKey: String

    fun init(apiKey: String, environment: ApiEnvironment): Module {

        apiEnvironment = environment
        this.apiKey = apiKey

        return dataModule
    }

    private val dataModule = module {

        single {
            provideOkHttpBuilder(get())
        }

        single {
            provideSharedPreferences(get(), get())
        }

        single {
            provideRetrofit(get(), get())
        }

        single {
            provideMoshi()
        }

        single {
            provideOrganizationApi(get())
        }

        single {
            provideUserApi(get())
        }

        single {
            provideGuestApi(get())
        }

        single {
            provideVodApi(get())
        }

        single {
            provideLiveApi(get())
        }

        single {
            provideProductApi(get())
        }

        single {
            provideScarlet(get(), get(), get(), get())
        }

        single {
            provideLiveWebSocketApi(get())
        }

        single {
            provideProductWebSocketApi(get())
        }
    }

    private const val TIMEOUT = 30

    private fun provideOkHttpBuilder(userSharedPreferences: PBJPreferences): OkHttpClient =
        OkHttpClient.Builder().apply {

            readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

            addInterceptor(AcceptInterceptor())
            addInterceptor(ApiKeyInterceptor(apiKey))
            addInterceptor(provideHttpLoggingInterceptor())
            addInterceptor(TimeZoneInterceptor())
            addInterceptor(AuthorizationInterceptor(userSharedPreferences))
        }.build()

    private fun provideSharedPreferences(context: Context, moshi: Moshi) = PBJPreferences(context, moshi)

    private fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UrlParser())
        .add(DateParser())
        .add(DateTimeParser())
        .build()

    private fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseApiUrl())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()

    private fun provideScarlet(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        preferences: PBJPreferences,
        application: Application
    ): Scarlet {

        val url =
            "${getBaseWebSocketUrl()}/episodes/stream?token=${preferences.userToken}&org_api_key=$apiKey"

        val requestFactory = object : RequestFactory {
            override fun createRequest(): Request = Request.Builder()
                .url(url)
                .build()
        }

        val connectionEstablisher: OkHttpWebSocket.ConnectionEstablisher =
            OkHttpClientWebSocketConnectionEstablisher(okHttpClient, requestFactory)

        val webSocketFactory: WebSocket.Factory = OkHttpWebSocket.Factory(connectionEstablisher)
        val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)

        return Scarlet.Builder()
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .webSocketFactory(webSocketFactory)
            .backoffStrategy(backoffStrategy)
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
            .build()
    }

    private fun provideOrganizationApi(retrofit: Retrofit): OrganizationApi =
        retrofit.create(OrganizationApi::class.java)

    private fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    private fun provideGuestApi(retrofit: Retrofit): GuestApi =
        retrofit.create(GuestApi::class.java)

    private fun provideVodApi(retrofit: Retrofit): VodApi =
        retrofit.create(VodApi::class.java)

    private fun provideLiveApi(retrofit: Retrofit): LiveApi =
        retrofit.create(LiveApi::class.java)

    private fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    private fun provideLiveWebSocketApi(scarlet: Scarlet): LiveWebSocketApi =
        scarlet.create(LiveWebSocketApi::class.java)

    private fun provideProductWebSocketApi(scarlet: Scarlet): ProductWebSocketApi =
        scarlet.create(ProductWebSocketApi::class.java)

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor {
            Timber.d(it)
        }

        if (apiEnvironment.isDebug) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun getBaseApiUrl() = "https://${apiEnvironment.domain}/api/"

    private fun getBaseWebSocketUrl() = "wss://${apiEnvironment.domain}/ws"


}
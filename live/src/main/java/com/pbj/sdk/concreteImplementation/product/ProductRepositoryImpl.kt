package com.pbj.sdk.concreteImplementation.product

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.live.model.JsonWebSocketProductRequest
import com.pbj.sdk.concreteImplementation.product.model.asModel
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.ProductRepository
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate
import com.pbj.sdk.domain.vod.model.VodVideo
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val api: ProductApi,
    private val socketApi: ProductWebSocketApi,
    override val moshi: Moshi
) : BaseRepository(moshi), ProductRepository {

    override suspend fun fetchProductsFor(episode: Episode): Result<List<Product>> =
        apiCall(
            call = { api.fetchProductsForEpisode(episode.id) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            },
            onSuccess = { response ->
                response?.results?.mapNotNull {
                    it.product?.asModel
                }
            }
        )

    override suspend fun fetchProductsFor(video: VodVideo): Result<List<Product>> = apiCall(
        call = { api.fetchProductsForVideo(video.id) },
        onApiError = { e, code ->
            mapGenericError(code.code, e)
        },
        onSuccess = { response ->
            response?.results?.mapNotNull {
                it.product?.asModel
            }
        }
    )

    override suspend fun fetchHighlightedProducts(episode: Episode): Result<List<Product>> =
        apiCall(
            call = { api.fetchHighlightedProducts(episode.id) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            },
            onSuccess = { response ->
                response?.results?.mapNotNull {
                    it.product?.asModel
                }
            }
        )

    private fun registerForProductHighlights(episode: Episode) {
        socketApi.subscribe(
            JsonWebSocketProductRequest(
                "join-episode-featured-product-updates",
                episode.id
            )
        )
    }

    override suspend fun observeProductHighlights(episode: Episode): Flow<ProductUpdate> {
        registerForProductHighlights(episode)
        return socketApi.observeProductStreamUpdates().map { it.asModel }
    }

    override suspend fun unRegisterProductHighlights(episode: Episode) {
        socketApi.subscribe(
            JsonWebSocketProductRequest(
                "leave-episode-featured-product-updates",
                episode.id
            )
        )
    }
}
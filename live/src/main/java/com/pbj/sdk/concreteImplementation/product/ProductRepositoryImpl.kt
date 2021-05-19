package com.pbj.sdk.concreteImplementation.product

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.live.model.JsonWebSocketRequest
import com.pbj.sdk.concreteImplementation.product.model.asModel
import com.pbj.sdk.domain.GenericError
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.ProductRepository
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val api: ProductApi,
    private val socketApi: ProductWebSocketApi
) : BaseRepository(), ProductRepository {

    override suspend fun fetchProductsFor(episode: Episode): Result<List<Product>> =
        apiCall(
            call = { api.fetchProducts(episode.id) },
            onApiError = { _, _ -> GenericError.Unknown() },
            onSuccess = { response ->
                response?.results?.mapNotNull {
                    it.product?.asModel
                }
            }
        )

    override suspend fun fetchHighlightedProducts(episode: Episode): Result<List<Product>> =
        apiCall(
            call = { api.fetchHighlightedProducts(episode.id) },
            onApiError = { _, _ -> GenericError.Unknown() },
            onSuccess = { response ->
                response?.results?.mapNotNull {
                    it.product?.asModel
                }
            }
        )

    private fun registerForProductHighlights(episode: Episode) {
        socketApi.subscribe(
            JsonWebSocketRequest(
                """
        {
            "command": "join-episode-featured-product-updates",
            "episode_id": "${episode.id}"
        }
        """
            )
        )
    }

    override suspend fun observeProductHighlights(episode: Episode): Flow<ProductUpdate> {
        registerForProductHighlights(episode)
        return socketApi.observeProductStreamUpdates().map { it.asModel }
    }

    override suspend fun unRegisterProductHighlights(episode: Episode) {
        socketApi.subscribe(
            JsonWebSocketRequest(
                """
        {
            "command": "leave-episode-featured-product-updates",
            "episode_id": "${episode.id}"
        }
        """
            )
        )
    }
}
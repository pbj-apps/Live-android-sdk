package com.pbj.sdk.domain.chat

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.Episode
import kotlinx.coroutines.flow.Flow

interface LiveChatSource {

    suspend fun getChatMessages(livestream: Episode): Flow<List<ChatMessage>>
    suspend fun postChatMessage(
        username: String,
        message: String,
        livestream: Episode
    ): Result<Void>
}
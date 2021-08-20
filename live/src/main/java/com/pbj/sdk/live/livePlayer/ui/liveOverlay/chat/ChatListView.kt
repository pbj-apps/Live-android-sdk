package com.pbj.sdk.live.livePlayer.ui.liveOverlay.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.domain.chat.ChatMessage
import com.pbj.sdk.live.livePlayer.ui.LivePreviewData

@Composable
fun ChatListView(modifier: Modifier = Modifier, messageList: List<ChatMessage>) {
    LazyColumn(modifier = modifier, reverseLayout = true) {
        items(messageList) { message ->
            Row {
                Text(
                    text = message.username,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Preview
@Composable
private fun ChatListPreview() {
    ChatListView(messageList = LivePreviewData.chatMessageList)
}
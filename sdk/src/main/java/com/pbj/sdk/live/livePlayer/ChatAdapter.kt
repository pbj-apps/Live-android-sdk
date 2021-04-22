package com.pbj.sdk.live.livePlayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pbj.sdk.R
import com.pbj.sdk.domain.chat.ChatMessage

internal class ChatAdapter(messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatMessageVH>() {

    private var chatMessageList: List<ChatMessage> = messages

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_message_item, parent, false)
        return ChatMessageVH(view)
    }

    override fun onBindViewHolder(holder: ChatMessageVH, position: Int) {
        holder.setup(chatMessageList[position])
    }

    fun update(messages: List<ChatMessage>) {
        chatMessageList = messages
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = chatMessageList.count()
}

class ChatMessageVH(view: View) : RecyclerView.ViewHolder(view) {

    private var nicknameView: AppCompatTextView = view.findViewById(R.id.nickname)
    private var messageView: AppCompatTextView = view.findViewById(R.id.message)

    fun setup(chatMessage: ChatMessage) {
        nicknameView.text = chatMessage.username
        messageView.text = chatMessage.text
    }
}


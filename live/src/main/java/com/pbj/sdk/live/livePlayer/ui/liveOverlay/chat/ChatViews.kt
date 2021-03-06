package com.pbj.sdk.live.livePlayer.ui.liveOverlay.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R

@Composable
fun ChatButton(modifier: Modifier = Modifier, messageCount: String, onClickChatButton: () -> Unit) {
    Row(
        modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClickChatButton()
            }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_chat),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = messageCount,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun ChatButtonPreview() {
    ChatButton(messageCount = "148") {}
}


@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    textMessage: String?,
    onChatTextChange: (String) -> Unit,
    send: () -> Unit
) {
    var isSearchFocused by remember {
        mutableStateOf(false)
    }

    val hint = stringResource(R.string.type_a_message)
    var text: String by remember {
        mutableStateOf(hint)
    }

    val hasText: Boolean = !textMessage.isNullOrBlank()

    text = if (isSearchFocused && !hasText)
        ""
    else if (!isSearchFocused && !hasText)
        hint
    else
        textMessage ?: ""

    val textColor = if (hasText) Color.White else Color.White.copy(0.75f)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White.copy(0.5f)),
        color = Color.Transparent
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                onValueChange = { message ->
                    onChatTextChange(message)
                },
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.None
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { send() }),
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentHeight()
                    .weight(1f)
                    .onFocusChanged {
                        isSearchFocused = it.isFocused
                    },
                singleLine = true,
                cursorBrush = SolidColor(Color.White)
            )

            Icon(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        send()
                    }
                    .padding(8.dp)
                    .size(24.dp),
                tint = if (text.isBlank()) Color.White.copy(0.5f) else Color.White
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ChatInputPreview() {
    ChatInput(textMessage = null, onChatTextChange = {}) {}
}
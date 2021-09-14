package com.pbj.sdk.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pbj.sdk.R


@Composable
fun UnderlinedTextField(
    modifier: Modifier = Modifier,
    textMessage: String?,
    hint: String = "",
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    var isSearchFocused by remember {
        mutableStateOf(false)
    }

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

    val effectiveTextStyle =
        if (hasText) textStyle
        else textStyle.copy(color = colorResource(R.color.dividerColor))

    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Column {
            BasicTextField(
                value = text,
                onValueChange = { message ->
                    onTextChange(message)
                },
                textStyle = effectiveTextStyle,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .wrapContentHeight()
                    .onFocusChanged {
                        isSearchFocused = it.isFocused
                    },
                enabled = enabled,
                singleLine = singleLine,
                maxLines = maxLines,
                readOnly = readOnly,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                cursorBrush = cursorBrush,
                decorationBox = decorationBox
            )

            Divider(thickness = 1.dp)
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun UnderlinedTextFieldPreview() {
    UnderlinedTextField(textMessage = null, hint = "Type your text here", onTextChange = {})
}
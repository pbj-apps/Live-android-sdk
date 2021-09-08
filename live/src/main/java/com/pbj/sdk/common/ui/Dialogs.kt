package com.pbj.sdk.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R


@Composable
fun TextFieldDialog(
    title: String,
    hint: String,
    description: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirm: (String) -> Unit,
    onDismiss: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    AlertDialog(
        title = {
            Text(
                text = title,
                fontSize = 20.sp
            )
        },
        text = {
            Column {
                Text(
                    description,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 14.sp
                )
                UnderlinedTextField(
                    Modifier.fillMaxWidth(),
                    textMessage = text,
                    hint = hint,
                    textStyle = TextStyle(
                        color = Color.Black.copy(alpha = 0.8f),
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.None
                    ),
                    singleLine = true,
                    onTextChange = {
                        text = it
                    },
                    cursorBrush = SolidColor(Color.Black.copy(alpha = 0.8f))
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.livePositiveButtonColor),
                    contentColor = colorResource(R.color.livePositiveButtonColor)
                ),
                onClick = {
                    onConfirm(text)
                }
            ) {
                Text(
                    text = confirmButtonText,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    color = colorResource(R.color.liveOnPositiveButtonColor)
                )
            }
        }, dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.liveCancelButtonColor)
                ),
                onClick = {
                    onDismiss(text)
                }
            ) {
                Text(
                    text = dismissButtonText,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            onDismiss(text)
                        }
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    color = colorResource(R.color.liveOnCancelButtonColor)
                )
            }
        },
        onDismissRequest = {}
    )
}

@Preview
@Composable
fun TextDialogPreview() {
    Box(Modifier.fillMaxSize()) {
        TextFieldDialog(
            title = "Title",
            hint = "Enter your text here",
            description = "Description",
            confirmButtonText = "Confirm",
            dismissButtonText = "Dismiss",
            {}
        ) {}
    }
}
package com.pbj.sdk.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pbj.sdk.R


@Composable
fun TextFieldDialog(
    title: String,
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
            Text(text = title)
        },
        text = {
            Column {
                Text(description)
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                    })
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.livePositiveButtonColor)
                ),
                onClick = {
                    onConfirm(text)
                }
            ) {
                Text(
                    text = dismissButtonText,
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
                    backgroundColor = colorResource(R.color.livePositiveButtonColor)
                ),
                onClick = {
                    onDismiss(text)
                }
            ) {
                Text(
                    text = confirmButtonText,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            onConfirm(text)
                        }
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    color = colorResource(R.color.liveOnPositiveButtonColor)
                )
            }
        },
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun TextDialogPreview() {
    TextFieldDialog(
        title = "Title",
        description = "Description",
        confirmButtonText = "Confirm",
        dismissButtonText = "Dismiss",
        {}
    ) {}
}
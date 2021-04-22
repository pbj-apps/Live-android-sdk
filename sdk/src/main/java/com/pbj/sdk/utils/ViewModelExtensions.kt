package com.pbj.sdk.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.launch(onError: ((Exception) -> Unit)? = null, block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (ex: Exception) {
            onError?.invoke(ex)
        }
    }
package com.pbj.sdk.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData

internal fun <T> AppCompatActivity.observe(liveData: MutableLiveData<T>, doOnChange: (T) -> Unit) {
    liveData.observe(this) {
        doOnChange.invoke(it)
    }
}

internal fun <T> Fragment.observe(liveData: MutableLiveData<T>, doOnChange: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) {
        doOnChange.invoke(it)
    }
}

internal fun FragmentActivity.startFragment(
    fragment: Fragment,
    containerView: Int,
    addToBackStack: Boolean = false
) {
    if (fragment.isAdded)
        return

    supportFragmentManager.beginTransaction()
        .apply {
            if (addToBackStack)
                addToBackStack(fragment.tag)
        }
        .replace(containerView, fragment)
        .commit()
}

internal fun FragmentManager.startFragment(
    fragment: Fragment,
    containerView: Int,
    addToBackStack: Boolean = false
) {
    if (fragment.isAdded)
        return

    beginTransaction()
        .apply {
            if (addToBackStack)
                addToBackStack(fragment.tag)
        }
        .replace(containerView, fragment)
        .commit()
}

internal fun FragmentManager.openInputTextDialog(
    title: String?,
    description: String?,
    hint: String?,
    positiveButtonText: String?,
    negativeButtonText: String?,
    listener: TextInputDialog.TextInputDialogListener
) {
    val dialog =
        TextInputDialog.newInstance(
            title,
            description,
            hint,
            positiveButtonText,
            negativeButtonText
        ).setListener(listener)

    dialog.show(this, null)
}
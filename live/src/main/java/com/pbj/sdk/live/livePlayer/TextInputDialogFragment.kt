package com.pbj.sdk.live.livePlayer

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pbj.sdk.R

class TextInputDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            TextInputDialogFragment()
    }
}
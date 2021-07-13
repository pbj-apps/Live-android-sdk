package com.pbj.sdk.utils


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.isVisible
import com.pbj.sdk.databinding.DialogTextInputBinding

class TextInputDialog : AppCompatDialogFragment() {

    private lateinit var listener: TextInputDialogListener

    lateinit var viewBinding: DialogTextInputBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DialogTextInputBinding.inflate(inflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        arguments?.apply {
            viewBinding.dialogTitle.setTextOrHide(getString(TITLE))
            viewBinding.dialogDescription.setTextOrHide(getString(DESCRIPTION))
            viewBinding.dialogEditText.hint = getString(HINT)
            viewBinding.dialogNegativeButton.text = getString(NEGATIVE_TEXT)
            viewBinding.dialogPositiveButton.text = getString(POSITIVE_TEXT)
        }

        viewBinding.dialogPositiveButton.setOnClickListener {
            listener.onClickPositiveButton(viewBinding.dialogEditText.text.toString())
            dismiss()
        }

        viewBinding.dialogNegativeButton.setOnClickListener {
            dismiss()
        }
    }


    fun setListener(dialogListener: TextInputDialogListener): TextInputDialog {

        listener = dialogListener
        return this
    }


    private fun TextView.setTextOrHide(string: String?) {
        string?.let {
            text = string
        } ?: run {
            isVisible = false
        }
    }


    interface TextInputDialogListener {
        fun onClickPositiveButton(text: String)
    }


    companion object {
        fun newInstance(
            title: String?,
            description: String?,
            hint: String?,
            positiveButtonText: String?,
            negativeButtonText: String?
        ): TextInputDialog = TextInputDialog().apply {
            arguments = Bundle().also {
                it.putString(TITLE, title)
                it.putString(DESCRIPTION, description)
                it.putString(HINT, hint)
                it.putString(POSITIVE_TEXT, positiveButtonText)
                it.putString(NEGATIVE_TEXT, negativeButtonText)
            }
        }

        private const val TITLE = "TITLE"
        private const val DESCRIPTION = "DESCRIPTION"
        private const val HINT = "HINT"
        private const val POSITIVE_TEXT = "POSITIVE_TEXT"
        private const val NEGATIVE_TEXT = "NEGATIVE_TEXT"
    }

}
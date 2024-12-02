package com.capstone.scancamanalyze.edittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.scancamanalyze.R
import com.google.android.material.textfield.TextInputLayout

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): AppCompatEditText(context, attrs){

    private var parentLayout: TextInputLayout? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (parentLayout != null && !isValidInput(s)) {
                    parentLayout?.error = context.getString(R.string.error_email)
                } else {
                    parentLayout?.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun isValidInput(input: CharSequence?): Boolean {
        return !input.isNullOrEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    fun setParentLayout(textInputLayout: TextInputLayout) {
        this.parentLayout = textInputLayout
    }

    fun isValid(): Boolean {
        val input = text?.toString()
        return !input.isNullOrEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }
}

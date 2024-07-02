package com.crosoften.emnuvem.ultils

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import com.crosoften.emnuvem.R
import com.google.android.material.textfield.TextInputEditText

class DigitValidCustomLayout (
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    private var incorrectTokenListener: (() -> Unit?)? = null
    private var inputListener: ((String) -> Unit)? = null
    private val inputFirstDigit: TextInputEditText? by lazy { findViewById(R.id.first_digit) }
    private val inputSecondDigit: TextInputEditText? by lazy { findViewById(R.id.second_input_digit) }
    private val inputThirdDigit: TextInputEditText? by lazy { findViewById(R.id.third_input_digit) }
    private val inputFourthDigit: TextInputEditText? by lazy { findViewById(R.id.fourth_input_digit) }

    init {
        inflate(context, R.layout.custom_digit_valid_layout, this)
        initComponents()
    }

    private fun initComponents() {
        inputFirstDigit?.doOnTextChanged { text, _, _, _ ->
            validToken()
            if (!text.isNullOrEmpty()) {
                inputSecondDigit?.requestFocus()
            }
        }

        inputSecondDigit?.doOnTextChanged { text, _, _, _ ->
            validToken()
            if (!text.isNullOrEmpty()) {
                inputThirdDigit?.requestFocus()
            } else {
                inputFirstDigit?.requestFocus()
            }
        }

        inputThirdDigit?.doOnTextChanged { text, _, _, _ ->
            validToken()
            if (!text.isNullOrEmpty()) {
                inputFourthDigit?.requestFocus()
            } else {
                inputSecondDigit?.requestFocus()
            }
        }

        inputFourthDigit?.doOnTextChanged { text, _, _, _ ->
            validToken()
            if (text.isNullOrEmpty()) {
                inputThirdDigit?.requestFocus()
            }
        }
    }

    private fun validToken() {
        ifNotNull(
            inputFirstDigit?.text.toString(),
            inputSecondDigit?.text.toString(),
            inputThirdDigit?.text.toString(),
            inputFourthDigit?.text.toString(),
            { firstDigit, secondDigit, thirdDigit, fourthDigit ->
                val token = "$firstDigit$secondDigit$thirdDigit$fourthDigit"
                inputListener?.invoke(token)
            }
        ) {
            incorrectTokenListener?.invoke()
        }
    }

//    fun setListeners(inputListener: (String) -> Unit, incorrectTokenListener: () -> Unit) {
//        this.inputListener = inputListener
//        this.incorrectTokenListener = incorrectTokenListener
//    }

    private inline fun ifNotNull(
        a: String?,
        b: String?,
        c: String?,
        d: String?,
        code: (String, String, String, String) -> Unit?,
        nullListener: () -> Unit
    ) {
        if (!a.isNullOrEmpty() && !b.isNullOrEmpty() && !c.isNullOrEmpty() && !d.isNullOrEmpty()) {
            code(a, b, c, d)
        } else {
            nullListener.invoke()
        }
    }

}
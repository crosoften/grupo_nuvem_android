package com.crosoften.emnuvem.ultils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object MaskEditUtil {
    const val FORMAT_CPF = "###.###.###-##"
    const val FORMAT_PHONE = "(##) #####-####"
    const val FORMAT_CEP = "#####-###"
    const val FORMAT_DATE = "##/##/####"
    const val FORMAT_MONEY = "###,###.##"
    const val FORMAT_HOUR = "##:##"
    const val FORMAT_YEAR = "####"
    const val FORMAT_CNPJ = "##.###.###/####-##"


    fun mask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    fun maskDocument(ediTxt: EditText, initialMask: String): TextWatcher {
        return object : TextWatcher {
            private var isUpdating = false
            private var currentMask = initialMask

            override fun afterTextChanged(s: Editable) {
                // Não precisa de nenhuma ação aqui
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val unmaskedText = unmask(s.toString())

                if (isUpdating) {
                    old = unmaskedText
                    isUpdating = false
                    return
                }
                // Determina qual máscara usar (CPF ou CNPJ) baseado no comprimento do texto sem formatação
                currentMask = if (unmaskedText.length > 11) FORMAT_CNPJ else FORMAT_CPF

                var maskedText = ""
                var i = 0

                for (m in currentMask.toCharArray()) {
                    if (m != '#' && unmaskedText.length > old.length) {
                        maskedText += m
                        continue
                    }
                    maskedText += try {
                        unmaskedText[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                ediTxt.setText(maskedText)
                ediTxt.setSelection(maskedText.length)
                isUpdating = false
            }

            private var old = ""
        }
    }


    fun maskMoney(ediTxt: EditText): TextWatcher {
        return object : TextWatcher {
            private var current = ""
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    isUpdating = true // Impede chamadas recursivas

                    // Remove a formatação
                    val cleanString = s.toString().replace("[R$,.\\s]".toRegex(), "")

                    if (cleanString.isNotEmpty()) {
                        // Converte o valor limpo para BigDecimal e formata
                        val parsed = cleanString.toBigDecimal().setScale(2, BigDecimal.ROUND_FLOOR).divide(BigDecimal(100))
                        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
                        formatter.applyPattern("#,##0.00")

                        // Formata o número e atualiza o campo
                        val formatted = formatter.format(parsed)

                        // Atualiza o texto
                        current = formatted
                        ediTxt.setText(formatted)

                        // Coloca o cursor no final
                        ediTxt.setSelection(formatted.length)
                    } else {
                        // Se a string limpa estiver vazia, limpa o campo
                        current = ""
                        ediTxt.setText("")
                    }

                    isUpdating = false // Permite chamadas futuras
                }
            }
        }
    }

    fun unMaskMoney(s: String): Double {
        val cleanString = s.replace("[,.\\s]".toRegex(), "")
        return cleanString.toDoubleOrNull()?.div(100) ?: 0.0
    }

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "")
            .replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}

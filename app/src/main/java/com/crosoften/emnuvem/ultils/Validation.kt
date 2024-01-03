package com.crosoften.emnuvem.ultils

import android.text.TextUtils

object Validation {

    /**
     * Função validadora de CPF
     * */
    fun isCPF(document: String): Boolean {
        if (TextUtils.isEmpty(document)) return false

        val numbers = arrayListOf<Int>()

        document.filter { it.isDigit() }.forEach {
            numbers.add(it.toString().toInt())
        }

        if (numbers.size != 11) return false

        //repeticao
        (0..9).forEach { n ->
            val digits = arrayListOf<Int>()
            (0..10).forEach { digits.add(n) }
            if (numbers == digits) return false
        }

        //digito 1
        val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }

    /**
     * Função validadora de E-mail
     */

    fun isEmailValid(email: String?): Boolean {
        if (email.isNullOrEmpty())
            return false

        return android
            .util
            .Patterns
            .EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    /**
     * Função validadora de numero celular
     */
    fun isPhoneValid(phoneNumber: String): Boolean {
        return phoneNumber.matches(".((10)|([1-9][1-9]).)\\s9?\\s[6-9][0-9]{3}-[0-9]{4}".toRegex()) || phoneNumber.matches(
            ".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}".toRegex()
        )
    }
}
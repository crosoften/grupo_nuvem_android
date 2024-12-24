package com.emnuvem.ultils.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import com.emnuvem.ultils.Validation.isEmailValid
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.isValidEmail(): Boolean {
    return isEmailValid(this)
}

fun String.isStrongPassword(): Boolean {
    return (this.length >= 6)
}

fun String?.notString(): Boolean {
    return !this.isNullOrEmpty()
}
fun String.isValidZipCode(): Boolean {
    return matches(Regex("\\d{5}-\\d{3}"))
}

fun String.isValidFullDate(): Boolean {
    return (this.length == 10)
}

fun String.isValidPhone(): Boolean {
    return matches(Regex("^\\d{10,11}$"))
}


fun String.isValidCPF(): Boolean {
    return (this.length == 14)
}

fun String.isValidQuantity(): Boolean {
    return when {
        isNullOrEmpty() -> false
        this.toInt() <= 0 -> false
        this == "null" -> false
        else -> true
    }

}

fun String.isValidCNPJ(): Boolean {
    val cleanCNPJ = this.replace(Regex("[^\\d]"), "")
    if (cleanCNPJ.length != 14) return false

    if (cleanCNPJ.all { it == cleanCNPJ[0] }) return false

    val weights1 = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    val weights2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    val digit1 = calculateDigit(cleanCNPJ.substring(0, 12), weights1)
    val digit2 = calculateDigit(cleanCNPJ.substring(0, 12) + digit1, weights2)

    return cleanCNPJ.endsWith("$digit1$digit2")
}

private fun calculateDigit(cnpj: String, weights: IntArray): Int {
    val sum = cnpj.mapIndexed { index, char ->
        char.digitToInt() * weights[index]
    }.sum()
    val remainder = sum % 11
    return if (remainder < 2) 0 else 11 - remainder
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return LocalDate.parse(this, formatter)
}


fun String.clearToLong(): Long {
    return this
        .replace("[^0-9]".toRegex(), "")
        .toLong()
}
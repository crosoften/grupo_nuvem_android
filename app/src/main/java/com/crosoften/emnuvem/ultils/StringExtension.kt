package com.crosoften.emnuvem.ultils

import android.os.Build
import androidx.annotation.RequiresApi
import com.crosoften.emnuvem.ultils.Validation.isEmailValid
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
    return (this.length == 9)
}

fun String.isValidFullDate(): Boolean {
    return (this.length == 10)
}

fun String.isValidPhone(): Boolean {
    return (this.length == 15)
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
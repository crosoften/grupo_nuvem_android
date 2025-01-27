package com.dnuv.ultils

import android.content.Context

fun calculateScreenWidth(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels
}

val states = listOf(
    "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
)
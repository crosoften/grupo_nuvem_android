package com.crosoften.emnuvem.utils

import android.content.Context

fun calculateScreenWidth(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels
}
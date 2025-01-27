package com.dnuv.ultils.extensions

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.dnuv.R
import com.google.android.material.snackbar.Snackbar

//fun Fragment.showError(message: String){
//    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
//}

fun Fragment.showError(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        .setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.red))
        .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        .show()
}

fun Fragment.showSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        .setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary))
        .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        .show()
}
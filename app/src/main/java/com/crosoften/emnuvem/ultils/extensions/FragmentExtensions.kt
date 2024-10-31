package com.crosoften.emnuvem.ultils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(message: String){
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}
package com.raju.mvvm.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        message,
        length
    ).show()
}
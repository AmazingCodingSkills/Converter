package com.currency.converter.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService

fun View.showKeyboard(): Boolean = context.getSystemService<InputMethodManager>()
    ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT) ?: false

fun View.hideKeyboard(): Boolean = context.getSystemService<InputMethodManager>()
    ?.hideSoftInputFromWindow(windowToken, 0) ?: false

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}



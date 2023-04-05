package com.converter.core.presentation

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.showKeyboard(): Boolean = context.getSystemService<InputMethodManager>()
    ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT) ?: false

fun View.hideKeyboard(): Boolean = context.getSystemService<InputMethodManager>()
    ?.hideSoftInputFromWindow(windowToken, 0) ?: false




package org.sjhstudio.flow.bookproject.presentation.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
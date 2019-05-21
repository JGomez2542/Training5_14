package com.example.mytrainingapp.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.ViewGroup
import com.example.mytrainingapp.kotlinReview.Contact

fun Contact.printAll() {
    println("Name:${this.name} Phone:${this.phone} Favorite: ${this.favorite}")
}

fun Context.snackbar(message: String, action: String, rootLayout: ViewGroup) {
    Snackbar.make(
        rootLayout,
        message, Snackbar.LENGTH_SHORT
    )
        .setAction(action) {}
        .show()
}
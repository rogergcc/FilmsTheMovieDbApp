package com.rogergcc.filmsthemoviedbapp.ui.myutils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.rogergcc.filmsthemoviedbapp.R



fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}


fun Activity.snackbar(msg: String, action: (() -> Unit)? = null) {
    Snackbar.make(
        findViewById(android.R.id.content),
        msg,
        Snackbar.LENGTH_LONG
    ).also {
        it.setAction(
            getString(R.string.ok)
        ) { action?.invoke() }
    }.show()
}
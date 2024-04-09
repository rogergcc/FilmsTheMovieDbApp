package com.rogergcc.filmsthemoviedbapp.presentation.utils

import android.view.View


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}
//fun sendData{
//    try {
//        val inputStream = contentResolver.openInputStream(uri)
//        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
//        val jsonString = bufferedReader.readText()
//
//        // Ahora tienes el contenido del archivo en jsonString
//        // Puedes procesarlo según tus necesidades
//
//        inputStream?.close()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
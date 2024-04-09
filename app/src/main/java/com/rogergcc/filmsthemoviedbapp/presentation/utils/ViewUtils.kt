package com.rogergcc.filmsthemoviedbapp.presentation.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.rogergcc.filmsthemoviedbapp.R


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

fun ImageView.loadImage(url: String, @DrawableRes placeholder: Int = R.drawable.movie_placeholder) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .error(R.drawable.movie_error)
        .apply(RequestOptions().centerCrop())
        .into(this)

}

fun ImageView.loadUrl(
    context: Context,
    url: String,
) {
    Glide.with(context).load(url).centerCrop()
        .error(R.drawable.movie_error)
        .into(this)
}

fun ImageView.loadUrlLoading(context: Context, url: String) {
    val requestOptions = RequestOptions()
        .fitCenter()
        .placeholder(R.drawable.movie_placeholder)
        .error(R.drawable.movie_error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .transform(CenterCrop())

    Glide.with(context)
        .asBitmap()
        .apply(requestOptions)
        .load(url)
        .into(this)
}

fun loadImageFromUrl(
    context: Context,
    urlPhoto: String,
    targetView: ImageView,
) {
    val requestOptions = RequestOptions()
        .fitCenter()
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .transform(CenterCrop())

    Glide.with(context)
        .asBitmap()
        .apply(requestOptions)
        .load(urlPhoto)
        .into(targetView)
}

fun ImageView.loadImageFromResource(@DrawableRes resourceId: Int) {
    Glide.with(context)
        .load(resourceId)
//        .centerCrop()
//        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)

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
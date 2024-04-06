package com.rogergcc.filmsthemoviedbapp.ui.utils

//import com.webserveis.mysubscriptions.R
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import java.util.*


/*
https://stackoverflow.com/questions/50617598/how-to-declare-startactivityforresult-in-one-line-in-kotlin
 */


fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, textId, duration).show() }


fun Context.getCompatColor(@ColorRes colorId: Int) =
    ResourcesCompat.getColor(resources, colorId, null)

fun Context.getCompatDrawable(@DrawableRes drawableId: Int) =
    AppCompatResources.getDrawable(this, drawableId)!!


@RequiresPermission(android.Manifest.permission.VIBRATE)
fun Context.vibrate(pattern: LongArray = longArrayOf(0, 150)) {
    val vibrator =
        applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator? ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE)
        )

    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(pattern, -1)
    }
}


fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

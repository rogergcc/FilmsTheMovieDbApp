package com.rogergcc.filmsthemoviedbapp.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
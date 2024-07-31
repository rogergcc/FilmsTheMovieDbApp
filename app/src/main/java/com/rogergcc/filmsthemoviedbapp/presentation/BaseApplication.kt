package com.rogergcc.filmsthemoviedbapp.presentation

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.rogergcc.filmsthemoviedbapp.BuildConfig
import com.rogergcc.filmsthemoviedbapp.application.TimberAppLogger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {
    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
            private set

        fun getApplicationContext(): Context {
            return INSTANCE.applicationContext
        }

        //        fun applicationContext() : Context = INSTANCE.applicationContext
        fun getAppResources(): Resources? = INSTANCE.resources
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            TimberAppLogger.init()
        }
        // initialize for any
        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context = BaseApplication.getApplicationContext()
    }
}
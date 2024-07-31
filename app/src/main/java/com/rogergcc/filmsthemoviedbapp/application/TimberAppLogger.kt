package com.rogergcc.filmsthemoviedbapp.application

import com.rogergcc.filmsthemoviedbapp.BuildConfig

import timber.log.Timber

object TimberAppLogger {
    fun d(s: String?, vararg objects: Any?) {
        Timber.d(s, *objects)
    }

    fun d(throwable: Throwable?, s: String?, vararg objects: Any?) {
        Timber.d(throwable, s, *objects)
    }

    fun e(s: String?, vararg objects: Any?) {
        Timber.e(s, *objects)
    }

    fun e(throwable: Throwable?, s: String?, vararg objects: Any?) {
        Timber.e(throwable, s, *objects)
    }

    fun info(s: String?, vararg objects: Any?) {
        Timber.i(s, *objects)
    }

    fun info(throwable: Throwable?, s: String?, vararg objects: Any?) {
        Timber.i(throwable, s, *objects)
    }

    fun init() {
            Timber.plant(Timber.DebugTree())
    }

    fun warning(s: String?, vararg objects: Any?) {
        Timber.w(s, *objects)
    }

    fun warning(throwable: Throwable?, s: String?, vararg objects: Any?) {
        Timber.w(throwable, s, *objects)
    }
}
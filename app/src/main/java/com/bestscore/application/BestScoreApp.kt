package com.bestscore.application

import android.app.Application

class BestScoreApp: Application() {

    companion object {
        private var _instance: BestScoreApp? = null
        val instance
            get() = _instance!!
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }
}
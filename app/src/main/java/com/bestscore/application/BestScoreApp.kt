package com.bestscore.application

import android.app.Application
import android.widget.Toast
import com.bestscore.database.di.DatabaseDi

class BestScoreApp: Application() {
    override fun onCreate() {
        super.onCreate()
        _instance = this

        DatabaseDi.initializeDb(instance.applicationContext)
        Toast.makeText(applicationContext, "Init db", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private var _instance: BestScoreApp? = null
        val instance
            get() = _instance!!
    }
}
package com.bestscore.database.di

import android.content.Context
import androidx.room.Room
import com.bestscore.database.ScoreDatabase
import com.bestscore.database.templates.TemplateDao

object DatabaseDi {
    lateinit var appDatabase: ScoreDatabase

    val templateDao: TemplateDao by lazy { appDatabase.getTemplateDao() }

    fun initializeDb(context: Context) {
        appDatabase = Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "best_score"
        ).build()
    }
}
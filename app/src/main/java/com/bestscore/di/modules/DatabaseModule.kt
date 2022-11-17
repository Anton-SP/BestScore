package com.bestscore.di.modules

import android.content.Context
import androidx.room.Room
import com.bestscore.core.annotations.di.AppScope
import com.bestscore.database.ScoreDatabase
import com.bestscore.database.templates.TemplateDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @[AppScope Provides]
    fun providesDb(context: Context): ScoreDatabase = Room
        .databaseBuilder(context, ScoreDatabase::class.java, DB_NAME)
        .build()

    @[AppScope Provides]
    fun providesTemplateDao(database: ScoreDatabase): TemplateDao = database.getTemplateDao()

}

const val DB_NAME = "database.db"
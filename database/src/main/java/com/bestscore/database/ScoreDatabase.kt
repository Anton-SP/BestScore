package com.bestscore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestscore.database.templates.ParameterEntity
import com.bestscore.database.templates.TemplateDao
import com.bestscore.database.templates.TemplateEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        TemplateEntity::class,
        ParameterEntity::class
    ]
)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun getTemplateDao(): TemplateDao
}
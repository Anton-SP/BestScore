package com.bestscore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bestscore.database.entity.ParameterEntity
import com.bestscore.database.entity.TemplateEntity

@Database(entities = [TemplateEntity::class,ParameterEntity::class], version = 1)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun getTemplateDao():TemplateDAO
}
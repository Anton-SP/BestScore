package com.bestscore.database.templates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: TemplateEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParametersList(parameters: List<ParameterEntity>)

    @Query("SELECT * FROM templates ORDER BY created_at DESC")
    fun getTemplateList(): List<TemplateEntity>
}
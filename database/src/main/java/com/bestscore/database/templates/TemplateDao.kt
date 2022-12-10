package com.bestscore.database.templates

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM templates ORDER BY id DESC LIMIT $LATEST_TEMPLATES_LIMIT")
    fun getLatestTemplateList(): List<TemplateEntity>

    @Delete
    suspend fun delete(template: TemplateEntity): Int

    companion object {
        const val LATEST_TEMPLATES_LIMIT = 3
    }
    @Query("SELECT * FROM parameters WHERE template_id = :templateId")
    fun getTemplateParameters(templateId:Long): List<ParameterEntity>
}
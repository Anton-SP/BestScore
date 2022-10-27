package com.bestscore.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bestscore.database.entity.TemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDAO {

    @Transaction
    @Query("SELECT * FROM TemplateEntity")
    fun getTemplateWithParameters(): Flow<List<Template>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTemplate(newTemplate:TemplateEntity)


}
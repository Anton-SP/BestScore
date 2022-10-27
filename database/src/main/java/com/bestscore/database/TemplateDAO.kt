package com.bestscore.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TemplateDAO {

    @Transaction
    @Query("SELECT * FROM TemplateEntity")
    fun getTemplateWithParameters(): List<Template>
}
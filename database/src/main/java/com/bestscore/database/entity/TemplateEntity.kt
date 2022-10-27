package com.bestscore.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TemplateEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_template")
    val idTemplate: Long,

    val name: String,

    @ColumnInfo(name = "create_at")
    val createAt: String,

    @ColumnInfo(name = "update_at")
    val updateAt: String,

    @ColumnInfo(name = "last_used_at")
    val lastUsedAt: String
)


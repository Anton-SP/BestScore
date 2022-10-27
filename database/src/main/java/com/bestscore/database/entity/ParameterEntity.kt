package com.bestscore.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParameterEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_parameter")
    val idParameter: Long,

    @ColumnInfo(name = "master_template_id")
    val masterTemplateId:Long,

    val name: String,

    val value: Int,

    @ColumnInfo(name = "is_counting")
    val isCounting: Boolean

)

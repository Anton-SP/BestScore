package com.bestscore.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TemplateEntity(

    @PrimaryKey
    val idTemplate: Long,

    val name: String,

    val createAt: String,

    val updateAt: String
)

@Entity
data class ParameterEntity(

    @PrimaryKey
    val idParameter: Long,

    val masterTemplateId:Long,

    val name: String,

    val isCounting: Boolean

)

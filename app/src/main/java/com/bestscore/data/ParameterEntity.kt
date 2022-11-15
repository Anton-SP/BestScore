package com.bestscore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParameterEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val value: Int,
    val is_counting: Boolean
)
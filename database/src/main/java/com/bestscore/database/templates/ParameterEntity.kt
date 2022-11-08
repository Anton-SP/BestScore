package com.bestscore.database.templates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "parameters",
    indices = [
        Index(
            value = ["player_name"], unique = false
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = TemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["template_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ParameterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "player_name")
    val playerName: String,

    @ColumnInfo(name = "start_value")
    val startValue: Int,

    @ColumnInfo(name = "take_when_calc")
    val takeWhenCalc: Boolean,

    @ColumnInfo(name = "template_id")
    val templateId: Long
)
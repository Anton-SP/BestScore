package com.bestscore.database

import androidx.room.Embedded
import androidx.room.Relation
import com.bestscore.database.entity.ParameterEntity
import com.bestscore.database.entity.TemplateEntity

data class Template(
    @Embedded
    val templateEntity: TemplateEntity,
    @Relation(
        parentColumn = "idTemplate",
        entityColumn = "masterTemplateId"
    )
    val parameters: List<ParameterEntity>
)
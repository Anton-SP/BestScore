package com.bestscore.database

import androidx.room.Embedded
import androidx.room.Relation

data class Template(
    @Embedded
    val templateEntity: TemplateEntity,
    @Relation(
        parentColumn = "idTemplate",
        entityColumn = "masterTemplateId"
    )
    val parameters: List<ParameterEntity>
)
package com.bestscore.utils

import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.database.templates.ParameterEntity
import com.bestscore.database.templates.TemplateEntity

fun Parameter.toEntity(templateId: Long) = ParameterEntity(
    id = id,
    parameterName = parameterName,
    startValue = startValue,
    takeWhenCalc = takeWhenCalc,
    templateId = templateId
)

fun Template.toEntity() = TemplateEntity(
    id = 0,
    name = name,
    createdAt = createdAt
)
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
    id = id,
    name = name,
    createdAt = createdAt
)

fun TemplateEntity.toTemplate(parameters: List<Parameter>) = Template (
    id = id,
    name = name,
    createdAt = createdAt,
    parameters = parameters
)

fun List<ParameterEntity>.toParameters() = this.map {
    Parameter(
        id = it.id,
        parameterName = it.parameterName,
        startValue = it.startValue,
        takeWhenCalc = it.takeWhenCalc
    )
}
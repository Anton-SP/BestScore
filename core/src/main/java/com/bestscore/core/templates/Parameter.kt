package com.bestscore.core.templates

import com.bestscore.database.templates.ParameterEntity

data class Parameter(
    var id: Long,
    var playerName: String,
    var startValue: Int,
    var takeWhenCalc: Boolean
){
    fun toEntity(templateId: Long) = ParameterEntity(
        id = id,
        playerName = playerName,
        startValue = startValue,
        takeWhenCalc = takeWhenCalc,
        templateId = templateId
    )
}


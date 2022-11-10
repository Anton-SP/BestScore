package com.bestscore.core.templates

data class Parameter(
    var id: Long,
    var parameterName: String,
    var startValue: Int,
    var takeWhenCalc: Boolean
)


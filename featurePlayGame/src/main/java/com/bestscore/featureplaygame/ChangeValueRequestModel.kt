package com.bestscore.featureplaygame

import com.bestscore.core.templates.Parameter

data class ChangeValueRequestModel(
    val data:List<Parameter>,
    val index: Int,
    val valueOfChange:Int
)

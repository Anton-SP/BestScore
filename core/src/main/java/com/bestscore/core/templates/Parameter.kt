package com.bestscore.core.templates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parameter(
    var id: Long,
    var parameterName: String,
    var startValue: Int,
    var takeWhenCalc: Boolean
) : Parcelable


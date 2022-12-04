package com.bestscore.core.templates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class TempTemplate(
    val id: Long,
    val name: String,
    val createdAt: Date,
    val parameters: List<Parameter>
) : Parcelable


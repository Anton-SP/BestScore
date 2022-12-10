package com.bestscore.core.templates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Template(
    val id: Long,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date,
    val parameters: List<Parameter>,
) : Parcelable


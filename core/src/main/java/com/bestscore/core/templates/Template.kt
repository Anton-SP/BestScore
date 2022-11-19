package com.bestscore.core.templates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Template(
    val id: Long,
    val name: String,
    val createdAt: Date
) : Parcelable


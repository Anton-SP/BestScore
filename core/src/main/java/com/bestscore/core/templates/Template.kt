package com.bestscore.core.templates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Template(
    val id: Long,
    val name: String
) : Parcelable

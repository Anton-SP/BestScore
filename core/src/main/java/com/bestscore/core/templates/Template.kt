package com.bestscore.core.templates

import com.bestscore.database.templates.TemplateEntity

data class Template(
    val id: Long,
    val name: String
) {
    fun toEntity() = TemplateEntity(
        id = 0,
        name = name
    )
}

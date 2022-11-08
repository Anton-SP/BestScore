package com.bestscore.repository

import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template

interface TemplateRepository {
    suspend fun create(template: Template, parameters: List<Parameter>)
}
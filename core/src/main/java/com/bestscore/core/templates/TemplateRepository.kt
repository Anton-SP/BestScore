package com.bestscore.core.templates

interface TemplateRepository {
    suspend fun create(template: Template, parameters: List<Parameter>)
    suspend fun getTemplates(): List<Template>
}
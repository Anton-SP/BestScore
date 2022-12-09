package com.bestscore.core.templates

interface TemplateRepository {
    suspend fun create(template: Template): Long
    suspend fun getTemplates(): List<Template>
    suspend fun getLatestTemplates(): List<Template>
}
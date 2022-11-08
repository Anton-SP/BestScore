package com.bestscore.featurecreatetemplate

import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.database.templates.TemplateDao
import com.bestscore.repository.TemplateRepository

class RoomTemplateRepository(
    private val dao: TemplateDao
) : TemplateRepository {
    override suspend fun create(template: Template, parameters: List<Parameter>) {
        val templateId = dao.insertTemplate(template = template.toEntity())
        if (templateId > 0) {
            dao.insertParametersList(parameters = parameters.map { it.toEntity(templateId) })
        }
    }
}
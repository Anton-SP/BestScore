package com.bestscore.repository

import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.database.templates.TemplateDao
import com.bestscore.utils.toEntity
import com.bestscore.utils.toParameters
import com.bestscore.utils.toTemplate
import javax.inject.Inject

class RoomTemplateRepository @Inject constructor(
    private val dao: TemplateDao
) : TemplateRepository {
    override suspend fun create(template: Template): Long {
        val templateId = dao.insertTemplate(template = template.toEntity())
        if (templateId > 0) {
            dao.insertParametersList(parameters = template.parameters.map { it.toEntity(templateId) })
            return templateId
        }

        return templateId
    }

    override suspend fun getTemplates(): List<Template> {
        return dao.getTemplateList().map { entity ->
            entity.toTemplate(
                dao.getTemplateParameters(entity.id).toParameters()
            )
        }
    }

    override suspend fun getLatestTemplates(): List<Template> {
        return dao.getLatestTemplateList().map { entity ->
            entity.toTemplate(
                dao.getTemplateParameters(entity.id).toParameters()
            )
        }
    }

}
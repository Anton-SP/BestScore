package com.bestscore.repository

import com.bestscore.core.dispatchers.DispatchersProvider
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.database.templates.TemplateDao
import com.bestscore.utils.toEntity
import com.bestscore.utils.toTemplate
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomTemplateRepository @Inject constructor(
    private val dao: TemplateDao,
    private val dispatchersProvider: DispatchersProvider
) : TemplateRepository {

    override suspend fun create(template: Template, parameters: List<Parameter>): Long =
        withContext(dispatchersProvider.io()) {
            val templateId = dao.insertTemplate(template = template.toEntity())
            if (templateId > 0) {
                dao.insertParametersList(parameters = parameters.map { it.toEntity(templateId) })
                return@withContext templateId
            }

            return@withContext templateId
        }

    override suspend fun getTemplates(): List<Template> = withContext(dispatchersProvider.io()) {
        return@withContext dao.getTemplateList().map { entity -> entity.toTemplate() }
    }

    override suspend fun getLatestTemplates(): List<Template> =
        withContext(dispatchersProvider.io()) {
            return@withContext dao.getLatestTemplateList().map { entity -> entity.toTemplate() }
        }

}
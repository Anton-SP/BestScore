package com.bestscore.repository

import com.bestscore.core.dispatchers.DispatchersProvider
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.database.templates.TemplateDao
import com.bestscore.utils.toEntity
import com.bestscore.utils.toParameters
import com.bestscore.utils.toTemplate
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomTemplateRepository @Inject constructor(
    private val dao: TemplateDao,
    private val dispatchersProvider: DispatchersProvider
) : TemplateRepository {
    override suspend fun create(template: Template): Long  = withContext(dispatchersProvider.io()){
        val templateId = dao.insertTemplate(template = template.toEntity())
        if (templateId > 0) {
            dao.insertParametersList(parameters = template.parameters.map { it.toEntity(templateId) })
            return@withContext templateId
        }

        return@withContext TEMPLATE_WAS_NOT_CREATED
    }

    override suspend fun getTemplates(): List<Template> = withContext(dispatchersProvider.io()) {
        return@withContext dao.getTemplateList().map { entity ->
            entity.toTemplate(
                dao.getTemplateParameters(entity.id).toParameters()
            )
        }
    }

    override suspend fun getLatestTemplates(): List<Template> = withContext(dispatchersProvider.io()) {
        return@withContext dao.getLatestTemplateList().map { entity ->
            entity.toTemplate(
                dao.getTemplateParameters(entity.id).toParameters()
            )
        }
    }

    override suspend fun delete(template: Template): Int = withContext(dispatchersProvider.io()){
        return@withContext dao.delete(template.toEntity())
    }

    companion object {
        const val TEMPLATE_WAS_NOT_CREATED = -1L
    }
}

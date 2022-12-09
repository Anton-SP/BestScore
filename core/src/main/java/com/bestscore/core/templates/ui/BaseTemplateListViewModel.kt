package com.bestscore.core.templates.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseTemplateListViewModel(
    private val repository: TemplateRepository
) : ViewModel() {
    protected val listStateFlow = MutableStateFlow<TemplateListState>(TemplateListState.Loading)
    open fun getListFlow() = listStateFlow.asStateFlow()

    private val deleteStateFlow = MutableStateFlow<TemplateDeleteState>(TemplateDeleteState.Nothing)
    open fun getDeleteFlow() = deleteStateFlow.asStateFlow()

    open fun deleteTemplate(template: Template) {
        listStateFlow.value = TemplateListState.Loading
        viewModelScope.launch {
            val result = repository.delete(template)

            val emitData = if (result > 0) {
                TemplateDeleteState.Success
            } else {
                TemplateDeleteState.Error("Не удалось удалить шаблон")
            }

            deleteStateFlow.emit(emitData)
        }
    }

    open fun notifiedAboutDeleteTemplate() {
        deleteStateFlow.value = TemplateDeleteState.Nothing
    }

    abstract fun getTemplateList()
}


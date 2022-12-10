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
    protected val stateFlow = MutableStateFlow<TemplateListState>(TemplateListState.Loading)
    open fun getStateFlow() = stateFlow.asStateFlow()

    open fun deleteTemplate(template: Template) {
        viewModelScope.launch {
            stateFlow.emit(TemplateListState.Loading)
            try {
                repository.delete(template)
                stateFlow.emit(TemplateListState.DeleteSuccess)
            } catch (e: Exception) {
                stateFlow.emit(TemplateListState.Error(message = "Не удалось удалить шаблон"))
            }
        }
    }

    abstract fun getTemplateList()
}


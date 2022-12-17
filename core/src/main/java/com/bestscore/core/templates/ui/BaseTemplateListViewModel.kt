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
                stateFlow.emit(TemplateListState.Error(message = MESSAGE_DONT_FIND_TEMPLATE))
            }
        }
    }

    abstract fun getTemplateList()

    companion object {
        const val MESSAGE_DONT_FIND_TEMPLATE = "Не удалось удалить игру"
        const val MESSAGE_EMPTY_LIST = "Вы еще не добавили ни одной игры"
        const val MESSAGE_LIST_ERROR = "При загрузке списка игр произошла ошибка"
    }
}


package com.bestscore.featuretemplatelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.core.templates.ui.BaseTemplateListViewModel
import com.bestscore.core.templates.ui.TemplateListState
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class TemplatesListViewModel(
    private val repository: TemplateRepository
) : BaseTemplateListViewModel(repository = repository) {

    override fun getTemplateList() {
        stateFlow.value = TemplateListState.Loading

        viewModelScope.launch {
            try {
                val templates = repository.getTemplates()
                stateFlow.emit(
                    TemplateListState.ListSuccess(data = templates)
                )
                if (templates.isEmpty()) {
                    stateFlow.emit(TemplateListState.Error(MESSAGE_EMPTY_LIST))
                }
            } catch (e: Exception) {
                stateFlow.emit(TemplateListState.Error(MESSAGE_LIST_ERROR))
            }
        }
    }

    class Factory @Inject constructor(
        private val repositoryProvider: Provider<TemplateRepository>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TemplatesListViewModel::class.java)
            return TemplatesListViewModel(repository = repositoryProvider.get()) as T
        }
    }
}
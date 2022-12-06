package com.bestscore.featuretemplatelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class TemplatesListViewModel(
    private val repository: TemplateRepository
) : ViewModel() {
    private val listStateFlow = MutableStateFlow<TemplatesListState>(TemplatesListState.Loading)
    fun getFlow(): StateFlow<TemplatesListState> = listStateFlow

    private val deleteStateFlow = MutableStateFlow<DeleteState>(DeleteState.Nothing)
    fun getDeleteFlow(): StateFlow<DeleteState> = deleteStateFlow

    fun getTemplateList() {
        listStateFlow.value = TemplatesListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val templates = repository.getTemplates()
            if (templates.isNotEmpty()) {
                listStateFlow.emit(
                    TemplatesListState.Success(data = templates)
                )
            } else {
                listStateFlow.emit(
                    TemplatesListState.Error(message = "Вы еще не добавили ни одного шаблона")
                )
            }
        }
    }

    fun deleteTemplate(template: Template) {
        listStateFlow.value = TemplatesListState.Loading
        viewModelScope.launch {
            val result = repository.delete(template)

            val emitData = if (result > 0) {
                DeleteState.Success
            } else {
                DeleteState.Error("Не удалось удалить шаблон")
            }

            deleteStateFlow.emit(emitData)
        }
    }

    fun notifiedAboutDeleteTemplate() {
        deleteStateFlow.value = DeleteState.Nothing
    }

    class Factory @Inject constructor(
        private val repositoryProvider: Provider<TemplateRepository>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TemplatesListViewModel::class.java)
            return TemplatesListViewModel(repository = repositoryProvider.get()) as T
        }
    }

    sealed class TemplatesListState {
        data class Success(val data: List<Template>): TemplatesListState()
        data class Error(val message: String): TemplatesListState()
        object Loading: TemplatesListState()
    }

    sealed class DeleteState {
        object Success : DeleteState()
        data class Error(val message: String) : DeleteState()
        object Nothing : DeleteState()
    }

}
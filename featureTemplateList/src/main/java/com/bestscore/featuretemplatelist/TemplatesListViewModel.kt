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
    private val stateFlow = MutableStateFlow<TemplatesListState>(TemplatesListState.Loading)

    fun getFlow(): StateFlow<TemplatesListState> = stateFlow

    fun getTemplateList() {
        stateFlow.value = TemplatesListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val templates = repository.getTemplates()
            if (templates.isNotEmpty()) {
                stateFlow.emit(
                    TemplatesListState.Success(data = templates)
                )
            } else {
                stateFlow.emit(
                    TemplatesListState.Error(message = "Вы еще не добавили ни одного шаблона")
                )
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

    sealed class TemplatesListState {
        data class Success(val data: List<Template>): TemplatesListState()
        data class Error(val message: String): TemplatesListState()
        object Loading: TemplatesListState()
    }

}
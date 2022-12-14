package com.bestscore.featurecreatetemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class CreateTemplateViewModel(
    private val repository: TemplateRepository
) : ViewModel() {
    private val createTemplateState = MutableStateFlow<CreateTemplateState>(CreateTemplateState.Nothing)
    fun getState(): StateFlow<CreateTemplateState> = createTemplateState

    fun save(template: Template) {
        val validationResult = Validator().validate(template, template.parameters)

        if (validationResult.success.not()) {
            createTemplateState.value = CreateTemplateState.Error(validationResult.message)
            createTemplateState.value = CreateTemplateState.Nothing
            return
        }

        viewModelScope.launch {
            createTemplateState.emit(CreateTemplateState.Nothing)
            val templateId = repository.create(template = template)
            if (templateId > 0) {
                createTemplateState.emit(CreateTemplateState.Success(template))
            } else {
                createTemplateState.emit(CreateTemplateState.Error("Не удалось сохранить игру"))
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: Provider<TemplateRepository>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CreateTemplateViewModel::class.java)
            return CreateTemplateViewModel(repository = repository.get()) as T
        }
    }

    sealed class CreateTemplateState {
        object Nothing : CreateTemplateState()
        data class Success(val template: Template) : CreateTemplateState()
        data class Error(val message: String) : CreateTemplateState()
    }
}
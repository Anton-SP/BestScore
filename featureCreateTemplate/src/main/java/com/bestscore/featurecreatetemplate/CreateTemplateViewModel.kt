package com.bestscore.featurecreatetemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.core.templates.TemplateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class CreateTemplateViewModel(
    private val repository: TemplateRepository
) : ViewModel() {

    fun save(template: Template, parameters: List<Parameter>) {
        viewModelScope.launch {
            repository.create(template = template, parameters = parameters)
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
}
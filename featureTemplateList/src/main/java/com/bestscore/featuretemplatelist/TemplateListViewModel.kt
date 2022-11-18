package com.bestscore.featuretemplatelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bestscore.core.templates.TemplateRepository
import javax.inject.Inject
import javax.inject.Provider

class TemplateListViewModel(
    private val repository: TemplateRepository
) : ViewModel() {



    class Factory @Inject constructor(
        private val repositoryProvider: Provider<TemplateRepository>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TemplateListViewModel::class.java)
            return TemplateListViewModel(repository = repositoryProvider.get()) as T
        }
    }
}
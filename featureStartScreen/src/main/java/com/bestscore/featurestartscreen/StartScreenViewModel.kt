package com.bestscore.featurestartscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.core.templates.ui.BaseTemplateListViewModel
import com.bestscore.core.templates.ui.TemplateListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class StartScreenViewModel (
    private val repository: TemplateRepository
) : BaseTemplateListViewModel(repository = repository) {

    override fun getTemplateList() {
        stateFlow.value = TemplateListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val templates = repository.getLatestTemplates()
            if (templates.isNotEmpty()) {
                stateFlow.emit(
                    TemplateListState.ListSuccess(data = templates)
                )
            } else {
                stateFlow.emit(
                    TemplateListState.Error(message = MESSAGE_EMPTY_LIST)
                )
            }
        }
    }

    class Factory @Inject constructor(
        private val repositoryProvider: Provider<TemplateRepository>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == StartScreenViewModel::class.java)
            return StartScreenViewModel(repository = repositoryProvider.get()) as T
        }
    }

    companion object {
        const val MESSAGE_EMPTY_LIST = "Вы еще не добавили ни одного шаблона"
    }
}
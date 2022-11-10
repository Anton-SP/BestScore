package com.bestscore.featurecreatetemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.database.di.DatabaseDi
import com.bestscore.repository.RoomTemplateRepository
import com.bestscore.core.templates.TemplateRepository
import kotlinx.coroutines.launch

class CreateTemplateViewModel(
    private val repository: TemplateRepository
): ViewModel() {

    fun save(template: Template, parameters: List<Parameter>) {
        viewModelScope.launch {
            repository.create(template = template, parameters = parameters)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CreateTemplateViewModel(RoomTemplateRepository(DatabaseDi.templateDao)) as T
            }
        }
    }
}
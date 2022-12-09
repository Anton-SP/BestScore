package com.bestscore.core.templates.ui

import com.bestscore.core.templates.Template

sealed class TemplateListState {
    data class Success(val data: List<Template>): TemplateListState()
    data class Error(val message: String): TemplateListState()
    object Loading: TemplateListState()
}
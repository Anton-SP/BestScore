package com.bestscore.core.templates.ui

sealed class TemplateDeleteState {
    object Success : TemplateDeleteState()
    data class Error(val message: String) :TemplateDeleteState()
    object Nothing : TemplateDeleteState()
}

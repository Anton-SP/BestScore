package com.bestscore.featurecreatetemplate.di

import androidx.lifecycle.ViewModel

internal class CreateTemplateComponentViewModel : ViewModel() {

    val newCreateTemplateComponent: CreateTemplateComponent =
        DaggerCreateTemplateComponent
            .builder()
            .dependencies(CreateTemplateDependenciesProvider.dependencies)
            .build()
}
package com.bestscore.featuretemplatelist.di

import androidx.lifecycle.ViewModel

internal class TemplatesListComponentViewModel : ViewModel() {
    val newTemplateListComponent: TemplatesListComponent =
        DaggerTemplatesListComponent
            .builder()
            .dependencies(TemplatesListDependenciesProvider.dependencies)
            .build()
}
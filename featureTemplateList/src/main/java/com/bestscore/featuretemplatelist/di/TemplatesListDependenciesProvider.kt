package com.bestscore.featuretemplatelist.di

interface TemplatesListDependenciesProvider {
    var dependencies: TemplatesListDependencies

    companion object : TemplatesListDependenciesProvider by TemplatesListDependenciesStore
}
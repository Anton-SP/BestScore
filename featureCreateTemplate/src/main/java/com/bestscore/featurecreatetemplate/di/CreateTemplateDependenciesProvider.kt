package com.bestscore.featurecreatetemplate.di

interface CreateTemplateDependenciesProvider {

    val dependencies: CreateTemplateDependencies

    companion object : CreateTemplateDependenciesProvider by CreateTemplateDependenciesStore

}
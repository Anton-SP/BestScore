package com.bestscore.featurecreatetemplate.di

import kotlin.properties.Delegates.notNull

object CreateTemplateDependenciesStore : CreateTemplateDependenciesProvider {
    override var dependencies: CreateTemplateDependencies by notNull()

}

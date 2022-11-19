package com.bestscore.featuretemplatelist.di

import kotlin.properties.Delegates.notNull

object TemplatesListDependenciesStore : TemplatesListDependenciesProvider {
    override var dependencies: TemplatesListDependencies by notNull()
}
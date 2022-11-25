package com.bestscore.featurestartscreen.di

import kotlin.properties.Delegates

object StartScreenDependenciesStore : StartScreenDependenciesProvider {
    override var dependencies: StartScreenDependencies by Delegates.notNull()
}
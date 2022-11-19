package com.bestscore.featurestartscreen.di

interface StartScreenDependenciesProvider {
    var dependencies: StartScreenDependencies

    companion object : StartScreenDependenciesProvider by StartScreenDependenciesStore
}
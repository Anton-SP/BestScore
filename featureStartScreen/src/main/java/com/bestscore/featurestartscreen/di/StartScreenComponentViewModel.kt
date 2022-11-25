package com.bestscore.featurestartscreen.di

import androidx.lifecycle.ViewModel


internal class StartScreenComponentViewModel : ViewModel() {
    val newStartScreenComponent : StartScreenComponent =
        DaggerStartScreenComponent
            .builder()
            .dependencies(StartScreenDependenciesProvider.dependencies)
            .build()
}
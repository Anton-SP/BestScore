package com.bestscore.featurestartscreen.di

import com.bestscore.core.annotations.di.FeatureScope
import com.bestscore.featurestartscreen.StartScreenFragment
import dagger.Component

@[FeatureScope Component(dependencies = [StartScreenDependencies::class])]
internal interface StartScreenComponent {

    fun inject(fragment: StartScreenFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(startScreenDependencies: StartScreenDependencies): Builder

        fun build(): StartScreenComponent

    }
}
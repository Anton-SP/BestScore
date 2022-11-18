package com.bestscore.featuretemplatelist.di

import com.bestscore.core.annotations.di.FeatureScope
import com.bestscore.featuretemplatelist.TemplatesListFragment
import dagger.Component


@[FeatureScope Component(dependencies = [TemplatesListDependencies::class])]
internal interface TemplatesListComponent {

    fun inject(fragment: TemplatesListFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(templatesListDependencies: TemplatesListDependencies): Builder

        fun build(): TemplatesListComponent
    }
}
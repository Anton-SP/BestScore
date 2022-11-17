package com.bestscore.featurecreatetemplate.di

import com.bestscore.core.annotations.di.FeatureScope
import com.bestscore.featurecreatetemplate.CreateTemplateFragment
import dagger.Component


@[FeatureScope Component(dependencies = [CreateTemplateDependencies::class])]
internal interface CreateTemplateComponent {

    fun inject(fragment: CreateTemplateFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(createTemplateDeps: CreateTemplateDependencies): Builder

        fun build(): CreateTemplateComponent
    }
}
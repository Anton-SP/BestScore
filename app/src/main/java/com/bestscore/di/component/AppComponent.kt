package com.bestscore.di.component

import android.app.Application
import com.bestscore.core.annotations.di.AppScope
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.di.modules.AppModule
import com.bestscore.featurecreatetemplate.di.CreateTemplateDependencies
import com.bestscore.featuretemplatelist.di.TemplatesListDependencies
import dagger.BindsInstance
import dagger.Component

@[AppScope Component(modules = [AppModule::class])]
interface AppComponent : CreateTemplateDependencies, TemplatesListDependencies {

    override val templateRepository: TemplateRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
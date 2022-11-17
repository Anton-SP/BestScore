package com.bestscore.di.modules

import com.bestscore.core.annotations.di.AppScope
import com.bestscore.core.templates.TemplateRepository
import com.bestscore.repository.RoomTemplateRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @[AppScope Binds]
    fun bindsTemplateRepository(implementation: RoomTemplateRepository): TemplateRepository

}
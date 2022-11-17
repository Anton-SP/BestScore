package com.bestscore.di.modules

import com.bestscore.core.dispatchers.DispatchersProvider
import com.bestscore.core.dispatchers.DispatchersProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface DispatchersModule {

    @Binds
    fun bindsDispatcherProvider(implementation: DispatchersProviderImpl): DispatchersProvider
}
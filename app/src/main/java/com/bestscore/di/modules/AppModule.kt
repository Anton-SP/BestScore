package com.bestscore.di.modules

import android.app.Application
import android.content.Context
import com.bestscore.core.annotations.di.AppScope
import dagger.Module
import dagger.Provides

@Module(
    includes = [DatabaseModule::class, DispatchersModule::class, RepositoryModule::class]
)
class AppModule {

    @[Provides AppScope]
    fun context(application: Application): Context = application.applicationContext
}
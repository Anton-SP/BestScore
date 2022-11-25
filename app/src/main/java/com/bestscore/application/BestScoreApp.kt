package com.bestscore.application

import android.app.Application
import com.bestscore.di.component.AppComponent
import com.bestscore.di.component.DaggerAppComponent
import com.bestscore.featurecreatetemplate.di.CreateTemplateDependenciesStore
import com.bestscore.featurestartscreen.di.StartScreenDependenciesStore
import com.bestscore.featuretemplatelist.di.TemplatesListDependenciesStore

class BestScoreApp : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(application = this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        CreateTemplateDependenciesStore.dependencies = appComponent
        TemplatesListDependenciesStore.dependencies = appComponent
        StartScreenDependenciesStore.dependencies = appComponent
    }
}
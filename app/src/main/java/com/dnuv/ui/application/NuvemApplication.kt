package com.dnuv.ui.application

import android.app.Application
import com.dnuv.data.di.apiModule
import com.dnuv.data.di.repositoryModules
import com.dnuv.data.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NuvemApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NuvemApplication)
            modules(viewModelModules, repositoryModules, apiModule)
        }
    }
}
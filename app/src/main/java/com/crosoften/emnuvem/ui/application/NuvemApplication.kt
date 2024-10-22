package com.crosoften.emnuvem.ui.application

import android.app.Application
import com.crosoften.emnuvem.data.di.apiModule
import com.crosoften.emnuvem.data.di.repositoryModules
import com.crosoften.emnuvem.data.di.viewModelModules
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
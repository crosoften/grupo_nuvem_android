package com.crosoften.emnuvem.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.crosoften.emnuvem.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EmNuvem: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        startKoin {
            androidContext(this@EmNuvem)
            modules(appModule)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
            private set
    }
}
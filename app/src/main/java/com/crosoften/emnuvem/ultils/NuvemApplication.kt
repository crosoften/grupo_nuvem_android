package com.crosoften.emnuvem.ultils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NuvemApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        startKoin {
            androidContext(this@NuvemApplication)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var applicationInstance: NuvemApplication? = null

        val instance: NuvemApplication
            get() = applicationInstance
                ?: throw IllegalStateException("NuvemApplication instance is not initialized. Make sure to initialize it in onCreate.")
    }
}
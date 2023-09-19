package com.crosoften.emnuvem.di

import com.crosoften.emnuvem.application.EmNuvem
import org.koin.dsl.module

val appModule = module {
    single {
        EmNuvem.instance
    }
}
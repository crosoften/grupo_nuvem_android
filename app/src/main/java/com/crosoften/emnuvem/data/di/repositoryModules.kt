package com.crosoften.emnuvem.data.di

import com.crosoften.emnuvem.data.repository.CameraRepository
import com.crosoften.emnuvem.data.repository.AuthRepository
import org.koin.dsl.module

val repositoryModules = module {
    single {
        CameraRepository(get())
    }
    single<AuthRepository> {
        AuthRepository(get())
    }
}
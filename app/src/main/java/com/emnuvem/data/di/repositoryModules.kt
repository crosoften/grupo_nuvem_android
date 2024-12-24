package com.emnuvem.data.di

import com.emnuvem.data.repository.CameraRepository
import com.emnuvem.data.repository.AuthRepository
import org.koin.dsl.module

val repositoryModules = module {
    single {
        CameraRepository(get())
    }
    single<AuthRepository> {
        AuthRepository(get())
    }
}
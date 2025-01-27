package com.dnuv.data.di

import com.dnuv.data.repository.CameraRepository
import com.dnuv.data.repository.AuthRepository
import org.koin.dsl.module

val repositoryModules = module {
    single {
        CameraRepository(get())
    }
    single<AuthRepository> {
        AuthRepository(get())
    }
}
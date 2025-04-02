package com.dnuv.data.di

import com.dnuv.data.repository.AuthRepository
import com.dnuv.data.repository.CameraRepository
import com.dnuv.data.repository.UserRepository
import com.dnuv.data.service.Service
import org.koin.dsl.module

val repositoryModules = module {
    single {
        CameraRepository(get())
    }
    single<AuthRepository> {
        AuthRepository(get())
    }
    single<UserRepository> {
        UserRepository(apiService = get())
    }
}
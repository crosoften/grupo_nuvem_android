package com.crosoften.emnuvem.data.di

import com.crosoften.emnuvem.data.repository.AddCamRepository
import com.crosoften.emnuvem.data.repository.ForgotRepository
import com.crosoften.emnuvem.data.repository.LoginRepository
import org.koin.dsl.module

val repositoryModules = module {
    single {
        AddCamRepository(get())
    }

    single {
        ForgotRepository(get())
    }

    single<LoginRepository> {
        LoginRepository(get())
    }
}
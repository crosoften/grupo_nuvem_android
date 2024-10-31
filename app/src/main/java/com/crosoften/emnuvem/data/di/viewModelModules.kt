package com.crosoften.emnuvem.data.di

import com.crosoften.emnuvem.viewModel.AddCamViewModel
import com.crosoften.emnuvem.viewModel.CamerasViewModel
import com.crosoften.emnuvem.viewModel.ForgotViewModel
import com.crosoften.emnuvem.viewModel.LoginViewModel
import com.crosoften.emnuvem.viewModel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        AddCamViewModel(get())
    }

    viewModel {
        ForgotViewModel(get())
    }
    viewModel {
        CamerasViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
}
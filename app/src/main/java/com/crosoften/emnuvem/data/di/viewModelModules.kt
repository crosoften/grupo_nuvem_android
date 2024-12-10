package com.crosoften.emnuvem.data.di

import com.crosoften.emnuvem.ui.viewModel.AddCamViewModel
import com.crosoften.emnuvem.ui.viewModel.CamerasViewModel
import com.crosoften.emnuvem.ui.viewModel.ContactViewModel
import com.crosoften.emnuvem.ui.viewModel.FaqViewModel
import com.crosoften.emnuvem.ui.viewModel.ForgotViewModel
import com.crosoften.emnuvem.ui.viewModel.LoginViewModel
import com.crosoften.emnuvem.ui.viewModel.RegisterViewModel
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

    viewModel {
        FaqViewModel(get())
    }

    viewModel {
        ContactViewModel(get())
    }
}
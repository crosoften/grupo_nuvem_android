package com.emnuvem.data.di

import com.emnuvem.ui.viewModel.AddCamViewModel
import com.emnuvem.ui.viewModel.CamerasViewModel
import com.emnuvem.ui.viewModel.ContactViewModel
import com.emnuvem.ui.viewModel.FaqViewModel
import com.emnuvem.ui.viewModel.ForgotViewModel
import com.emnuvem.ui.viewModel.LoginViewModel
import com.emnuvem.ui.viewModel.RegisterViewModel
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
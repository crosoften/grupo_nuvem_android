package com.dnuv.data.di

import com.dnuv.ui.viewModel.AddCamViewModel
import com.dnuv.ui.viewModel.CamerasViewModel
import com.dnuv.ui.viewModel.ContactViewModel
import com.dnuv.ui.viewModel.FaqViewModel
import com.dnuv.ui.viewModel.ForgotViewModel
import com.dnuv.ui.viewModel.LoginViewModel
import com.dnuv.ui.viewModel.RegisterViewModel
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
package com.routinely.routinely.changepassword

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val forgotPasswordModule = module {
    viewModel {
        ForgotPasswordViewModel(get())
    }
}
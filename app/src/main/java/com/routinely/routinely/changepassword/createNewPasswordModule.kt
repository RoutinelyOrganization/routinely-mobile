package com.routinely.routinely.changepassword

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createNewPasswordModule = module {
    viewModel<CreateNewPasswordViewModel> {
        CreateNewPasswordViewModel(get())
    }
}
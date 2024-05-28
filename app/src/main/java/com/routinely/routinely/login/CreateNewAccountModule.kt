package com.routinely.routinely.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createNewAccountModule = module {
    viewModel<CreateAccountViewModel> {
        CreateAccountViewModel(get())
    }
}

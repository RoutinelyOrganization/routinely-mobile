package com.routinely.routinely.task

import com.routinely.routinely.login.AddTaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addTaskModule = module {
    viewModel<AddTaskViewModel> {
        AddTaskViewModel(get())
    }
}
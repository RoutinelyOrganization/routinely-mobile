package com.routinely.routinely.task.di

import com.routinely.routinely.task.AddTaskViewModel
import com.routinely.routinely.task.EditTaskViewModel
import com.routinely.routinely.task.data.GetTaskByIdUseCase
import com.routinely.routinely.task.data.GetTaskByIdUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addTaskModule = module {
    viewModel<AddTaskViewModel> {
        AddTaskViewModel(get(), get())
    }
    viewModel<EditTaskViewModel> {
        EditTaskViewModel(get(), get(), get())
    }
    single<GetTaskByIdUseCase> {
        GetTaskByIdUseCaseImpl(get(), get())
    }
}
package com.routinely.routinely.home

import com.routinely.routinely.home.data.ExcludeTaskUseCase
import com.routinely.routinely.home.data.ExcludeTaskUseCaseImpl
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(get(), get(), get())
    }
    single<GetUserTasksFromMonthUseCase> {
        GetUserTasksFromMonthUseCaseImpl(get())
    }
    single<ExcludeTaskUseCase> {
        ExcludeTaskUseCaseImpl(get())
    }
}

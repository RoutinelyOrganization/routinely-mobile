package com.routinely.routinely.data.auth.di

import com.routinely.routinely.data.task.api.AddTaskApi
import com.routinely.routinely.data.task.api.AddTaskApiImpl
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.api.AuthApiImpl
import org.koin.dsl.module

val authModule = module {
    single<AddTaskApi> {
        AddTaskApiImpl(get())
    }
    single<AuthApi> {
        AuthApiImpl(get())
    }
}
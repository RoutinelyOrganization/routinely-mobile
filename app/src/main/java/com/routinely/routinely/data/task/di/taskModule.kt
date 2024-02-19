package com.routinely.routinely.data.task.di

import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.data.task.api.TaskApiImpl
import org.koin.dsl.module

val taskModule = module {
    single<TaskApi> {
        TaskApiImpl(get())
    }
}
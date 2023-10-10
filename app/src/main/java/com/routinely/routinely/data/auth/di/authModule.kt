package com.routinely.routinely.data.auth.di

import com.routinely.routinely.data.auth.api.RegisterApi
import com.routinely.routinely.data.auth.api.RegisterApiImpl
import org.koin.dsl.module

val authModule = module {
    single<RegisterApi> {
        RegisterApiImpl(get())
    }
}
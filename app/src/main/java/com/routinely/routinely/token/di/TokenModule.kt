package com.routinely.routinely.token.di

import com.routinely.routinely.token.IsLoggedInAndIsValidUseCase
import com.routinely.routinely.token.IsLoggedInAndIsValidUseCaseImpl
import org.koin.dsl.module

val tokenModule = module {
    single<IsLoggedInAndIsValidUseCase> {
        IsLoggedInAndIsValidUseCaseImpl(get(), get())
    }
}
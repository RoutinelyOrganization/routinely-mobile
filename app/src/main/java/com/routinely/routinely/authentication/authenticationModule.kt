package com.routinely.routinely.authentication

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authenticationModule = module {
    single<AuthenticationData> {
        AuthenticationData(androidContext(), get())
    }
}

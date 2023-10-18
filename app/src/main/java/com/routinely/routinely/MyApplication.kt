package com.routinely.routinely

import android.app.Application
import com.routinely.routinely.changepassword.createNewPasswordModule
import com.routinely.routinely.changepassword.forgotPasswordModule
import com.routinely.routinely.changepassword.verificationCodeModule
import com.routinely.routinely.data.auth.di.authModule
import com.routinely.routinely.data.core.coreModule
import com.routinely.routinely.home.homeModule
import com.routinely.routinely.login.createNewAccountModule
import com.routinely.routinely.login.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(authModule, coreModule, createNewAccountModule,
                loginModule, createNewPasswordModule, forgotPasswordModule,
                verificationCodeModule, homeModule)
        }
    }
}
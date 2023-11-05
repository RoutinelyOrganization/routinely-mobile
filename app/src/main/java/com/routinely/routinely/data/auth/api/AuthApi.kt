package com.routinely.routinely.data.auth.api

import com.routinely.routinely.data.auth.model.CreateAccountResult
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.data.auth.model.SignInResult

interface AuthApi {
    suspend fun registerUser(registerRequest: RegisterRequest) : CreateAccountResult

    suspend fun loginUser(loginRequest: LoginRequest) : SignInResult
}
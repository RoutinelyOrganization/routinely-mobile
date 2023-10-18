package com.routinely.routinely.data.auth.api

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.data.auth.model.SignInResult

interface AuthApi {
    suspend fun registerUser(registerRequest: RegisterRequest) : ApiResponse

    suspend fun loginUser(loginRequest: LoginRequest) : SignInResult
}
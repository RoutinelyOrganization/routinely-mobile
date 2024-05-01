package com.routinely.routinely.data.auth.api

import com.routinely.routinely.data.auth.model.CreateAccountResult
import com.routinely.routinely.data.auth.model.CreateNewPasswordRequest
import com.routinely.routinely.data.auth.model.CreateNewPasswordResult
import com.routinely.routinely.data.auth.model.ForgotPasswordRequest
import com.routinely.routinely.data.auth.model.ForgotPasswordResult
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.data.auth.model.SignInResult
import com.routinely.routinely.data.auth.model.ValidateCodeRequest
import com.routinely.routinely.data.auth.model.ValidateCodeResult

interface AuthApi {
    suspend fun registerUser(registerRequest: RegisterRequest) : CreateAccountResult

    suspend fun loginUser(loginRequest: LoginRequest) : SignInResult

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) : ForgotPasswordResult
    suspend fun validateCode(validateCodeRequest: ValidateCodeRequest) : ValidateCodeResult
    suspend fun createNewPassword(createNewPasswordRequest: CreateNewPasswordRequest): CreateNewPasswordResult
}
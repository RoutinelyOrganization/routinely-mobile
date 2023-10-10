package com.routinely.routinely.data.auth.api

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.RegisterRequest

interface RegisterApi {
    suspend fun registerUser(registerRequest: RegisterRequest) : ApiResponse
}
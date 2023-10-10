package com.routinely.routinely.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.auth.api.RegisterApi
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountViewModel(
    private val registerRequest: RegisterApi,
) : ViewModel() {

    suspend fun createNewAccount(createAccount: RegisterRequest): ApiResponse = withContext(Dispatchers.IO) {
        val params = RegisterRequest(
            name = createAccount.name,
            email = createAccount.email,
            password = createAccount.password,
            acceptedTerms = createAccount.acceptedTerms
        )
        val response: ApiResponse = registerRequest.registerUser(params)

        return@withContext ApiResponse(
            message = response.message,
            statusCode = response.statusCode,
            error = response.error,
        )
    }

    fun verifyAllConditions(name: String, email: String, password: String, acceptedTerms: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
        }
    }
}
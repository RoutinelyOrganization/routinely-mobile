package com.routinely.routinely.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.routinely.routinely.data.auth.api.RegisterApi
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.isPasswordValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAccountViewModel(
    private val registerRequest: RegisterApi,
) : ViewModel() {

    suspend fun createNewAccount(createAccount: RegisterRequest): ApiResponse = withContext(Dispatchers.IO) {
        if(verifyAllConditions(createAccount)){
            val response: ApiResponse = registerRequest.registerUser(createAccount)
            if(response.statusCode == null){
                shouldGoToNextScreen.value = true
            }
            return@withContext ApiResponse(
                message = response.message,
                statusCode = response.statusCode,
                error = response.error,
            )
        }else{
            return@withContext ApiResponse(
                message = listOf(),
                statusCode = null,
                error = null,
            )
        }
    }
    var shouldGoToNextScreen = mutableStateOf(false)
        private set
    private fun verifyName(name: String): Boolean{
        return name.length >= 3
    }
    private fun verifyEmail(email: String): Boolean{
        return isValidEmailFormat(email)
    }
    private fun verifyPassword(password: String): Boolean{
        return isPasswordValid(password)
    }
    private fun verifyAcceptedTerms(acceptedTerms: Boolean): Boolean{
        return acceptedTerms
    }

    private fun verifyAllConditions(createAccount: RegisterRequest) : Boolean {
        return verifyName(createAccount.name) &&
                verifyEmail(createAccount.email) &&
                verifyPassword(createAccount.password) &&
                verifyAcceptedTerms(createAccount.acceptedTerms)
    }
}
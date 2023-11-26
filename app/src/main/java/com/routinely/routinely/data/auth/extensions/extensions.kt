package com.routinely.routinely.data.auth.extensions

import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.CreateAccountResult
import com.routinely.routinely.data.auth.model.ForgotPasswordResponse
import com.routinely.routinely.data.auth.model.ForgotPasswordResult
import com.routinely.routinely.data.auth.model.LoginResponse
import com.routinely.routinely.data.auth.model.SignInResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

//suspend fun HttpResponse.toApiResponse() : ApiResponse {
//    val response =  this.body<ApiResponseBody>()
//    response.serverStatusCode = this.status
//    return response
//
//}

suspend fun HttpResponse.toSignInResult() : SignInResult {
    return when(this.status) {
        HttpStatusCode.OK -> {
            val response = this.body<LoginResponse>()
            SignInResult.Success(response.token)
        }
        HttpStatusCode.Unauthorized -> {
            SignInResult.Error(R.string.api_login_unauthorized)
        }
        else -> {
            SignInResult.DefaultError
        }
    }
}

fun HttpResponse.toCreateAccountResult() : CreateAccountResult {
    return when(this.status) {
        HttpStatusCode.Created -> {
            CreateAccountResult.Success
        }
        HttpStatusCode.UnprocessableEntity -> {
            CreateAccountResult.Error(R.string.api_create_account_already_exist_email)
        }
        else -> {
            CreateAccountResult.DefaultError
        }
    }
}

suspend fun HttpResponse.toForgotPasswordResult() : ForgotPasswordResult {
    return when(this.status) {
        HttpStatusCode.Created -> {
            val response = this.body<ForgotPasswordResponse>()
            ForgotPasswordResult.Success(response.accountId)
        }
        HttpStatusCode.NotFound -> {
            ForgotPasswordResult.Error(R.string.api_forgot_password_account_not_found)
        }
        else -> {
            ForgotPasswordResult.DefaultError
        }
    }
}
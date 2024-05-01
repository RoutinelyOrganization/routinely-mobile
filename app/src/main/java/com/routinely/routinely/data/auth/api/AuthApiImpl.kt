package com.routinely.routinely.data.auth.api

import com.routinely.routinely.R
import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.data.auth.extensions.toCreateAccountResult
import com.routinely.routinely.data.auth.extensions.toCreateNewPasswordResult
import com.routinely.routinely.data.auth.extensions.toForgotPasswordResult
import com.routinely.routinely.data.auth.extensions.toSignInResult
import com.routinely.routinely.data.auth.extensions.toValidateCodeResult
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
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

internal class AuthApiImpl(
    private val client: HttpClient
) : AuthApi {
    override suspend fun registerUser(registerRequest: RegisterRequest): CreateAccountResult {
        return try {
            client.post(HttpRoutes.REGISTER) {
                setBody(registerRequest)
                contentType(ContentType.Application.Json)
            }.toCreateAccountResult()
        } catch(e: ResponseException){
            handleCreateAccountErrorResponse(e.response.status)
        } catch(e: Exception){
            handleCreateAccountErrorResponse(HttpStatusCode(900, e.message ?: "Unknown Exception"))
        }
    }

    override suspend fun loginUser(loginRequest: LoginRequest) : SignInResult {
        return try {
            client.post(HttpRoutes.LOGIN) {
                setBody(loginRequest)
                contentType(ContentType.Application.Json)
            }.toSignInResult()
        }  catch(e: ResponseException){
            handleSignInErrorResponse(e.response.status)
        } catch(e: Exception){
            handleSignInErrorResponse(HttpStatusCode(900, e.message ?: "Unknown Exception"))
        }
    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResult {
        return try {
            client.post(HttpRoutes.FORGOT_PASSWORD) {
                setBody(forgotPasswordRequest)
                contentType(ContentType.Application.Json)
            }.toForgotPasswordResult()
        } catch(e: ResponseException){
            handleForgotPasswordError(e.response.status)
        } catch(e: Exception){
            handleForgotPasswordError(HttpStatusCode(900, e.message ?: "Unknown Exception"))
        }
    }

    override suspend fun validateCode(validateCodeRequest: ValidateCodeRequest): ValidateCodeResult {
        return try {
            client.post(HttpRoutes.VALIDATE_CODE) {
                setBody(validateCodeRequest)
                contentType(ContentType.Application.Json)
            }.toValidateCodeResult()
        } catch(e: ResponseException){
            handleValidateCodeError(e.response.status)
        } catch(e: Exception){
            handleValidateCodeError(HttpStatusCode(900, e.message ?: "Unknown Exception"))
        }
    }

    override suspend fun createNewPassword(createNewPasswordRequest: CreateNewPasswordRequest): CreateNewPasswordResult {
        return try {
            client.put(HttpRoutes.CHANGE_PASSWORD) {
                setBody(createNewPasswordRequest)
                contentType(ContentType.Application.Json)
            }.toCreateNewPasswordResult()
        } catch(e: ResponseException){
            handleCreateNewPasswordError(e.response.status)
        } catch(e: Exception){
            handleCreateNewPasswordError(HttpStatusCode(900, e.message ?: "Unknown Exception"))
        }
    }

    private fun handleSignInErrorResponse(httpStatusCode: HttpStatusCode): SignInResult {
        println("Error SignIn: ${httpStatusCode.description}")
        return SignInResult.DefaultError
    }
    private fun handleCreateAccountErrorResponse(httpStatusCode: HttpStatusCode): CreateAccountResult {
        println("Error SignIn: ${httpStatusCode.description}")
        return CreateAccountResult.Error(R.string.api_unexpected_error)
    }

    private fun handleForgotPasswordError(httpStatusCode: HttpStatusCode): ForgotPasswordResult {
        println("Error: ${httpStatusCode.description}")
        return ForgotPasswordResult.DefaultError
    }

    private fun handleValidateCodeError(httpStatusCode: HttpStatusCode): ValidateCodeResult {
        println("Error: ${httpStatusCode.description}")
        return ValidateCodeResult.DefaultError
    }

    private fun handleCreateNewPasswordError(httpStatusCode: HttpStatusCode): CreateNewPasswordResult {
        println("Error: ${httpStatusCode.description}")
        return CreateNewPasswordResult.DefaultError
    }
}

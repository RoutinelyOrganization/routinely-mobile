package com.routinely.routinely.data.auth.api

import com.routinely.routinely.R
import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.data.auth.extensions.toApiResponse
import com.routinely.routinely.data.auth.extensions.toSignInResult
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.data.auth.model.SignInResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

internal class AuthApiImpl(
    private val client: HttpClient
) : AuthApi {
    override suspend fun registerUser(registerRequest: RegisterRequest): ApiResponse {
        return try {
            client.post(HttpRoutes.REGISTER) {
                setBody(registerRequest)
                contentType(ContentType.Application.Json)
            }.toApiResponse()
        } catch(e: ResponseException){
            handleErrorResponse(e.response.status)
        } catch(e: Exception){
            handleErrorResponse(HttpStatusCode(900, e.message ?: "Unknown Exception"))
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

    private fun handleErrorResponse(httpStatusCode: HttpStatusCode): ApiResponse {
        println("Error: ${httpStatusCode.description}")
        return ApiResponse(listOf("Unknown Exception"), httpStatusCode)
    }
    private fun handleSignInErrorResponse(httpStatusCode: HttpStatusCode): SignInResult {
        println("Error SignIn: ${httpStatusCode.description}")
        return SignInResult.Error(R.string.api_unexpected_error)
    }
}

package com.routinely.routinely.data.auth.extensions

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.LoginResponse
import com.routinely.routinely.data.auth.model.ResponseStringTemp
import com.routinely.routinely.data.auth.model.SignInResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend fun HttpResponse.toApiResponse() : ApiResponse {
    return try {
        val response =  this.body<ApiResponse>()
        response.serverStatusCode = this.status
        return response
    } catch (e: io.ktor.serialization.JsonConvertException) {
        val temp = this.body<ResponseStringTemp>()
        val messageToList = listOf(temp.message)
        ApiResponse(
            message = messageToList,
            serverStatusCode = this.status
        )
    }
}

suspend fun HttpResponse.toSignInResult() : SignInResult {
    val apiResponse = this.toApiResponse()

    return if(apiResponse.serverStatusCode.value < 300) {
        val response = this.body<LoginResponse>()
        SignInResult.Success(response.token)
    } else {
        SignInResult.Error(apiResponse.message)
    }
}
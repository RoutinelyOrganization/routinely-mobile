package com.routinely.routinely.data.auth.extensions

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ResponseStringTemp
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
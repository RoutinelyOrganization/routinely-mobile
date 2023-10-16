package com.routinely.routinely.network.extensions

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.network.model.ResponseStringTemp
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend fun HttpResponse.toApiResponse() : ApiResponse {
    return try {
        this.body<ApiResponse>()
    } catch (e: io.ktor.serialization.JsonConvertException) {
        val temp = this.body<ResponseStringTemp>()
        val messageToList = listOf(temp.message)
        ApiResponse(
            message = messageToList,
            serverStatusCode = this.status
        )
    }
}
package com.routinely.routinely.data.auth.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseBody(
    val errors: ErrorsApi,
    @Contextual var serverStatusCode: HttpStatusCode,
)

@Serializable
data class ErrorsApi(
    val property: String?,
    val message: String,
)

@Serializable
internal data class ResponseStringTemp(
    val message: String,
)

sealed class ApiResponse {
    data object Success : ApiResponse()
    data class Error(val message: Int) : ApiResponse()
    data object DefaultError : ApiResponse()
    data object Loading : ApiResponse()
    data object Empty : ApiResponse()
}
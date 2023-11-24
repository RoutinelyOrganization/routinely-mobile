package com.routinely.routinely.data.task.extensions

import com.routinely.routinely.data.auth.model.ApiResponse
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode


fun HttpResponse.taskToApiResponse() : ApiResponse {
    return when(this.status) {
        HttpStatusCode.Created -> {
            ApiResponse.Success
        }
        else -> {
            ApiResponse.DefaultError
        }
    }
}
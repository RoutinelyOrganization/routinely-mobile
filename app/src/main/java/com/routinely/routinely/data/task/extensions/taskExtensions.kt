package com.routinely.routinely.data.task.extensions

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.util.TaskRemoteItem
import io.ktor.client.call.body
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

suspend fun HttpResponse.toTaskRemoteItemList() : List<TaskRemoteItem> {
    return when(this.status) {
        HttpStatusCode.OK -> {
            this.body<List<TaskRemoteItem>>()
        }
        else -> {
            emptyList()
        }
    }
}
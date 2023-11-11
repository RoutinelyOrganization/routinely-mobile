package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.task.extensions.taskToApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

internal class AddTaskApiImpl(
    private val client: HttpClient
) : AddTaskApi {
    override suspend fun addTask(addTaskRequest: AddTaskRequest): ApiResponse {
        return try {
            client.post(HttpRoutes.ADDTASK) {
                setBody(addTaskRequest)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${addTaskRequest.accountId}")
                }
            }.taskToApiResponse()
        } catch(e: RedirectResponseException){
            // 3xx - responses
            handleErrorResponse()
        } catch(e: ClientRequestException){
            // 4xx - responses
            handleErrorResponse()
        } catch(e: ServerResponseException){
            // 5xx - responses
            handleErrorResponse()
        } catch(e: Exception){
            handleErrorResponse()
        }
    }

    private fun handleErrorResponse(): ApiResponse {
        return ApiResponse.DefaultError
    }
}
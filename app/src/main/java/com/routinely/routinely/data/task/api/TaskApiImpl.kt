package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.task.extensions.taskToApiResponse
import com.routinely.routinely.data.task.extensions.toTaskRemoteItemList
import com.routinely.routinely.util.TaskRemoteItem
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.parameters

internal class TaskApiImpl(
    private val client: HttpClient
) : TaskApi {
    override suspend fun addTask(addTaskRequest: AddTaskRequest): ApiResponse {
        return try {
            client.post(HttpRoutes.TASK) {
                setBody(addTaskRequest)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${addTaskRequest.accountId}")
                }
            }.taskToApiResponse()
        } catch(e: RedirectResponseException){
            // 3xx - responses
            handleErrorApiErrorResponse()
        } catch(e: ClientRequestException){
            // 4xx - responses
            handleErrorApiErrorResponse()
        } catch(e: ServerResponseException){
            // 5xx - responses
            handleErrorApiErrorResponse()
        } catch(e: Exception){
            handleErrorApiErrorResponse()
        }
    }

    override suspend fun getMonthTasks(month: Int, year: Int, userId: String): List<TaskRemoteItem> {
        return try {
            client.get(HttpRoutes.TASK) {
                parameters {
                    parameter("month", month)
                    parameter("year", year)
                }
                headers {
                    append(HttpHeaders.Authorization, "Bearer $userId")
                }
            }.toTaskRemoteItemList()
        } catch(e: RedirectResponseException){
            // 3xx - responses
            handleErrorApiErrorResponse()
            emptyList()
        } catch(e: ClientRequestException){
            // 4xx - responses
            handleErrorApiErrorResponse()
            emptyList()
        } catch(e: ServerResponseException){
            // 5xx - responses
            handleErrorApiErrorResponse()
            emptyList()
        } catch(e: Exception){
            handleErrorApiErrorResponse()
            emptyList()
        }
    }

    private fun handleErrorApiErrorResponse(): ApiResponse {
        return ApiResponse.DefaultError
    }
}
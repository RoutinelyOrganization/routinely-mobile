package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.data.task.extensions.excludeToApiResponse
import com.routinely.routinely.data.task.extensions.taskToApiResponse
import com.routinely.routinely.data.task.extensions.taskUpdateToApiResponse
import com.routinely.routinely.data.task.extensions.toTaskItemList
import com.routinely.routinely.util.TaskItem
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.parameters

internal class TaskApiImpl(
    private val client: HttpClient
) : TaskApi {
    override suspend fun addTask(taskRequest: TaskRequest): ApiResponse {
        return try {
            client.post(HttpRoutes.TASK) {
                setBody(taskRequest)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${taskRequest.accountId}")
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

    override suspend fun getMonthTasks(month: Int, year: Int, userId: String): List<TaskItem> {
        return try {
            client.get(HttpRoutes.TASK) {
                parameters {
                    parameter("month", month)
                    parameter("year", year)
                }
                headers {
                    append(HttpHeaders.Authorization, "Bearer $userId")
                }
            }.toTaskItemList()
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

    override suspend fun excludeTask(taskId: Int, userId: String): ApiResponse {
        return try {
            client.delete((HttpRoutes.TASK)) {
                url {
                    appendPathSegments(taskId.toString())
                }
                headers {
                    append(HttpHeaders.Authorization, "Bearer $userId")
                }
            }.excludeToApiResponse()
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

    override suspend fun updateTask(taskId: Int, taskRequest: TaskRequest): ApiResponse {
        return try {
            val response = client.put(HttpRoutes.TASK) {
                url {
                    appendPathSegments(taskId.toString())
                }
                setBody(taskRequest)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${taskRequest.accountId}")
                }
            }
            val test = response.taskUpdateToApiResponse()
            return test
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

    private fun handleErrorApiErrorResponse(): ApiResponse {
        return ApiResponse.DefaultError
    }
}
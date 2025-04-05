package com.routinely.routinely.data.task.extensions

import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.TaskItemRemote
import com.routinely.routinely.util.TaskListResponse
import com.routinely.routinely.util.TaskPriorities
import com.routinely.routinely.util.TaskTag
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import timber.log.Timber


@Serializable
data class ErrorResponse(
    val errors: List<ErrorDetail>,
)

@Serializable
data class ErrorDetail(
    val property: String?,
    val message: String?,
)

suspend fun HttpResponse.taskToApiResponse(): ApiResponse {
    return when (this.status) {
        HttpStatusCode.Created -> {
            ApiResponse.Success
        }

        else -> {
            try {
                val errorResponse = this.body<ErrorResponse>()
                if (errorResponse.errors.isNotEmpty()) {
                    ApiResponse.Error(message = R.string.api_unexpected_error)
                } else {
                    ApiResponse.DefaultError
                }
            } catch (e: Exception) {
                println("Erro ao processar resposta: ${e.message}")
                ApiResponse.DefaultError
            }
        }
    }
}

suspend fun HttpResponse.taskUpdateToApiResponse(): ApiResponse {
    return when (this.status) {
        HttpStatusCode.OK -> {
            ApiResponse.Success
        }

        else -> {
            val test = this.body<Error>()
            println("Property ${test.errors?.property}  --  ${test.errors?.message}")
            ApiResponse.DefaultError
        }
    }
}

suspend fun HttpResponse.toTaskItemList(): ApiResponseWithData<List<TaskItem>> {
    return when (this.status) {
        HttpStatusCode.OK -> {
            try {
                val response =
                    this.body<TaskListResponse?>() ?: return ApiResponseWithData.EmptyData()
                if (response.tasks.isEmpty()) return ApiResponseWithData.EmptyData()

                ApiResponseWithData.Success(
                    response.tasks.map {
                        TaskItem(
                            id = it.id,
                            name = it.name,
                            date = it.date,
                            type = it.type.lowercase(),
                            category = it.category,
                            description = it.description,
                            finallyDate = it.finallyDate,
                            weekDays = it.weekDays
                        )
                    }
                )
            } catch (e: Exception) {
                ApiResponseWithData.DefaultError()
            }
        }

        else -> {
            ApiResponseWithData.DefaultError()
        }
    }
}

suspend fun HttpResponse.toTaskItem(): TaskItem? {
    return when (this.status) {
        HttpStatusCode.OK -> {
            val remote = this.body<TaskItemRemote?>()
            if (remote == null) return remote
            remote.let {
                TaskItem(
                    id = it.id,
                    name = it.name,
                    date = it.date,
                    type = it.type,
                    category = it.category,
                    description = it.description,
                    finallyDate = it.finallyDate,
                    weekDays = it.weekDays
                )
            }
        }

        else -> {
            println(this.status)
            null
        }
    }
}

@Serializable
data class Error(
    val errors: Errors?,
)

@Serializable
data class Errors(
    val property: String,
    val message: String,
)

fun HttpResponse.excludeToApiResponse(): ApiResponse {
    return when (this.status) {
        HttpStatusCode.OK -> {
            ApiResponse.Success
        }

        else -> {
            ApiResponse.DefaultError
        }
    }
}

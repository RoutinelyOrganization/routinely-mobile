package com.routinely.routinely.data.task.extensions

import android.util.Log
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.TaskItemRemote
import com.routinely.routinely.util.TaskPriorities
import com.routinely.routinely.util.TaskTag
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable


suspend fun HttpResponse.taskToApiResponse() : ApiResponse {
    return when(this.status) {
        HttpStatusCode.Created -> {
            ApiResponse.Success
        }
        else -> {
            val test = this.body<Error>()
            println("Property ${test.errors?.property}  --  ${test.errors?.message}")
            ApiResponse.DefaultError
        }
    }
}

suspend fun HttpResponse.taskUpdateToApiResponse() : ApiResponse {
    return when(this.status) {
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

suspend fun HttpResponse.toTaskItemList() : ApiResponseWithData<List<TaskItem>> {
    return when(this.status) {
        HttpStatusCode.OK -> {
            val remote = this.body<List<TaskItemRemote>?>() ?: return ApiResponseWithData.EmptyData()

            return ApiResponseWithData.Success(
                remote.map {
                    TaskItem(
                        id = it.id,
                        name = it.name,
                        date = it.date,
                        hour = it.hour,
                        description = it.description,
                        tag = mapApiStringToTag(it.tag),
                        priority = mapApiStringToPriority(it.priority),
                        category = mapApiStringToCategory(it.category)
                    )
                }
            )
        }
        else -> {
            ApiResponseWithData.DefaultError()
        }
    }
}

suspend fun HttpResponse.toTaskItem() : TaskItem? {
    return when(this.status) {
        HttpStatusCode.OK -> {
            val remote = this.body<TaskItemRemote?>()
            if(remote == null) return remote
            remote.let {
                TaskItem(
                    id = it.id,
                    name = it.name,
                    date = it.date,
                    hour = it.hour,
                    description = it.description,
                    tag = mapApiStringToTag(it.tag),
                    priority = mapApiStringToPriority(it.priority),
                    category = mapApiStringToCategory(it.category)
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
    val errors: Errors?
)

@Serializable
data class Errors(
    val property: String,
    val message: String
)

fun HttpResponse.excludeToApiResponse() : ApiResponse {
    return when(this.status) {
        HttpStatusCode.OK -> {
            ApiResponse.Success
        }
        else -> {
            ApiResponse.DefaultError
        }
    }
}

fun mapApiStringToCategory(apiString: String): TaskCategory {
    return when (apiString) {
        "career" -> TaskCategory.Career
        "personal" -> TaskCategory.Personal
        "health" -> TaskCategory.Health
        "finance" -> TaskCategory.Finances
        "study" -> TaskCategory.Studies
        else -> throw IllegalArgumentException("Unknown category: $apiString")
    }
}

private fun mapApiStringToPriority(apiString: String): TaskPriorities {
    return when (apiString) {
        "urgent" -> TaskPriorities.Urgent
        "high" -> TaskPriorities.High
        "medium" -> TaskPriorities.Medium
        "low" -> TaskPriorities.Low
        else -> throw IllegalArgumentException("Unknown priority: $apiString")
    }
}

private fun mapApiStringToTag(apiString: String): TaskTag {
    return when (apiString) {
        "application" -> TaskTag.Candidacy
        "account" -> TaskTag.Bill
        "exercise" -> TaskTag.Exercise
        "beauty" -> TaskTag.Beauty
        "literature" -> TaskTag.Literature
        else -> throw IllegalArgumentException("Unknown tag: $apiString")
    }
}
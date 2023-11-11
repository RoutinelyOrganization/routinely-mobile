package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse

interface AddTaskApi {
    suspend fun addTask(addTaskRequest: AddTaskRequest) : ApiResponse
}
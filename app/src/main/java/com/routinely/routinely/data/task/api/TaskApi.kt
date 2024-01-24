package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.util.TaskRemoteItem

interface TaskApi {
    suspend fun addTask(addTaskRequest: AddTaskRequest) : ApiResponse

    suspend fun getMonthTasks(month: Int, year: Int, userId: String) : List<TaskRemoteItem>
}
package com.routinely.routinely.data.task.api

import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskApi {
    suspend fun addTask(taskRequest: TaskRequest) : ApiResponse

    suspend fun getMonthTasks(month: Int, year: Int, userId: String) : Flow<ApiResponseWithData<List<TaskItem>>>

    suspend fun getTaskById(userId: String, taskId: Int): TaskItem?

    suspend fun excludeTask(taskId: Int, userId: String) : ApiResponse

    suspend fun updateTask(taskId: Int, taskRequest: TaskRequest) : ApiResponse
}
package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.task.api.TaskApi

internal class ExcludeTaskUseCaseImpl(
    private val taskApi: TaskApi
): ExcludeTaskUseCase{
    override suspend fun invoke(userId: String, taskId: Int): ApiResponse {
        return taskApi.excludeTask(taskId, userId)
    }
}
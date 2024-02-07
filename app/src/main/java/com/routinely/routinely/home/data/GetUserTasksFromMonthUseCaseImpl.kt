package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.data.task.api.TaskApi

internal class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi,
) : GetUserTasksFromMonthUseCase {
    override suspend fun invoke(month: Int, year: Int, userId: String): ApiResponseWithData {
        val tasks = taskApi.getMonthTasks(month, year, userId)
        return if(tasks.isEmpty()) {
            ApiResponseWithData.EmptyData
        } else {
            ApiResponseWithData.Success(tasks)
        }
    }
}
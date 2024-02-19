package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

internal class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi,
) : GetUserTasksFromMonthUseCase {
    override suspend fun invoke(month: Int, year: Int, userId: String): Flow<ApiResponseWithData<List<TaskItem>>> = flow {
        taskApi.getMonthTasks(month, year, userId).collect {
            emit(it)
        }
    }
}
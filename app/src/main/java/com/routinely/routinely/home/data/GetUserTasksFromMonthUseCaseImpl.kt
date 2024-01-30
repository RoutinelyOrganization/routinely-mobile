package com.routinely.routinely.home.data

import android.util.Log
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.TaskItem

internal class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi,
) : GetUserTasksFromMonthUseCase {
    override suspend fun invoke(month: Int, year: Int, userId: String): List<TaskItem> {
        Log.d("GetUserTasksFromMonthUseCase", "invoke")
        return taskApi.getMonthTasks(month, year, userId)
    }
}
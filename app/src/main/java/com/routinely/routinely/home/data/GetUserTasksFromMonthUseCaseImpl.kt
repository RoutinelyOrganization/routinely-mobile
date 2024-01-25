package com.routinely.routinely.home.data

import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.ActionItem
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.TaskPriorities
import kotlinx.coroutines.runBlocking

internal class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi,
) : GetUserTasksFromMonthUseCase {
    override suspend fun invoke(month: Int, year: Int, userId: String): List<TaskItem> {
        return taskApi.getMonthTasks(month, year, userId)
    }
}
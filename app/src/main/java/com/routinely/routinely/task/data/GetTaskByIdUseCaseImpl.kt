package com.routinely.routinely.task.data

import com.routinely.routinely.core.Session
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.TaskItem


class GetTaskByIdUseCaseImpl(
    private val taskApi: TaskApi
) : GetTaskByIdUseCase {
    override suspend fun invoke(taskId: Int): TaskItem? {
        return taskApi.getTaskById(taskId = taskId)
    }
}
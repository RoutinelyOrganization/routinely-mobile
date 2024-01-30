package com.routinely.routinely.task.data

import android.util.Log
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.TaskItem

class GetTaskByIdUseCaseImpl(
    private val session: Session,
    private val taskApi: TaskApi
) : GetTaskByIdUseCase {
    override suspend fun invoke(id: Int, month: Int, year: Int): TaskItem? {
        Log.d("GetTaskByIdUseCase", "invoke")
        return taskApi.getMonthTasks(month, year, session.getToken()).firstOrNull { it.id == id }
    }
}
package com.routinely.routinely.task.data

import com.routinely.routinely.util.TaskItem

interface GetTaskByIdUseCase {
    suspend operator fun invoke(id: Int, month: Int, year: Int): TaskItem?
}
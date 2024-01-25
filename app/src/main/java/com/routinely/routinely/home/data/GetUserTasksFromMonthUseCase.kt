package com.routinely.routinely.home.data

import com.routinely.routinely.util.TaskItem

interface GetUserTasksFromMonthUseCase {
    suspend operator fun invoke(month: Int, year: Int, userId: String): List<TaskItem>
}
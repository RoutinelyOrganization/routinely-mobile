package com.routinely.routinely.home.data

import com.routinely.routinely.util.TaskItems

interface GetUserTasksFromMonthUseCase {
    suspend operator fun invoke(month: Int, year: Int, userId: String): List<TaskItems>
}
package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.Flow

interface GetUserTasksFromMonthUseCase {
    suspend operator fun invoke(month: Int, year: Int, day: Int, userId: String, force: Boolean = false): Flow<ApiResponseWithData<List<TaskItem>>>
}
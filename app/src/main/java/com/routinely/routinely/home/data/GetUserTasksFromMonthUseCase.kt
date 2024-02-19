package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.Flow

interface GetUserTasksFromMonthUseCase {
    suspend operator fun invoke(month: Int, year: Int, userId: String): Flow<ApiResponseWithData<List<TaskItem>>>
}
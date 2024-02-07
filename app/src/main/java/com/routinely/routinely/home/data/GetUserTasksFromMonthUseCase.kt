package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData

interface GetUserTasksFromMonthUseCase {
    suspend operator fun invoke(month: Int, year: Int, userId: String): ApiResponseWithData
}
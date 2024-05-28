package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponse

interface ExcludeTaskUseCase {
    suspend operator fun invoke(taskId: Int): ApiResponse
}
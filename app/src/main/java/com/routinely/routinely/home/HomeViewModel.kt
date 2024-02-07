package com.routinely.routinely.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.home.data.ExcludeTaskUseCase
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val session: Session,
    private val getUserTasksFromMonthUseCase: GetUserTasksFromMonthUseCase,
    private val excludeTaskUseCase: ExcludeTaskUseCase,
) : ViewModel() {

    private val _deleteTaskResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val deleteTaskResponse = _deleteTaskResponse.asStateFlow()

    private val _getTasksResponse = MutableStateFlow<ApiResponseWithData>(ApiResponseWithData.Default)
    val getTasksResponse = _getTasksResponse.asStateFlow()

    var lastMonth = 0
    var lastYear = 0

    fun logout() {
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }

    fun getUserTasks(month: Int, year: Int) = viewModelScope.launch {
        _getTasksResponse.value = ApiResponseWithData.Loading
        lastMonth = month
        lastYear = year
        try {
            _getTasksResponse.value = getUserTasksFromMonthUseCase(month, year, session.getToken())
        } catch (e: Exception) {
            _getTasksResponse.value = ApiResponseWithData.DefaultError
        }
    }

    fun excludeTask(task: TaskItem) = viewModelScope.launch {
        val userId = session.getToken()
        _deleteTaskResponse.value = excludeTaskUseCase(userId, task.id)
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}
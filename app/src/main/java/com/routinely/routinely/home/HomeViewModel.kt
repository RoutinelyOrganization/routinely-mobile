package com.routinely.routinely.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.home.data.ExcludeTaskUseCase
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val session: Session,
    private val getUserTasksFromMonthUseCase: GetUserTasksFromMonthUseCase,
    private val excludeTaskUseCase: ExcludeTaskUseCase,
) : ViewModel() {

    private val _tasksList = MutableStateFlow(listOf<TaskItem>())
    val tasksList = _tasksList.asStateFlow()

    private val _deleteTaskResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val deleteTaskResponse = _deleteTaskResponse.asStateFlow()

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
        Log.d(TAG, "getUserTasks: called")
        lastMonth = month
        lastYear = year
        delay(200)
        _tasksList.value = getUserTasksFromMonthUseCase(month, year, session.getToken())
    }

    fun excludeTask(task: TaskItem) = viewModelScope.launch {
        val userId = session.getToken()
        _deleteTaskResponse.value = excludeTaskUseCase(userId, task.id)
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}
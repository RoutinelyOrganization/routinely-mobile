package com.routinely.routinely.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {

    private val _tasksList = MutableStateFlow(listOf<TaskItem>())
    val tasksList = _tasksList.asStateFlow()

    private var lastMonth = 0
    private var lastYear = 0

    fun logout() {
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }

    fun getUserTasks(month: Int, year: Int) = viewModelScope.launch {
        if(month == lastMonth && year == lastYear) return@launch

        lastMonth = month
        lastYear = year
        _tasksList.value = getUserTasksFromMonthUseCase(month, year, session.getToken())
    }

    fun excludeTask(task: TaskItem) = viewModelScope.launch {
        val userId = session.getToken()
        excludeTaskUseCase(userId, task.id)
        _tasksList.value = getUserTasksFromMonthUseCase(lastMonth, lastYear, userId)

    }

}
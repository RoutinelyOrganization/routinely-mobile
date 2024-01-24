package com.routinely.routinely.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.util.TaskItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val session: Session,
    private val getUserTasksFromMonthUseCase: GetUserTasksFromMonthUseCase
): ViewModel() {

    private val _tasksList = MutableStateFlow(listOf<TaskItems>())
    val tasksList = _tasksList.asStateFlow()

    fun logout() {
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }

    fun getUserTasks(month: Int, year: Int) = viewModelScope.launch {
        _tasksList.value = getUserTasksFromMonthUseCase(month, year, session.getToken())
    }

}
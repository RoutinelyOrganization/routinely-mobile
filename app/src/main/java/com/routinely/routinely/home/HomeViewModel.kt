package com.routinely.routinely.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.home.data.ExcludeTaskUseCase
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(
    private val session: Session,
    private val getUserTasksFromMonthUseCase: GetUserTasksFromMonthUseCase,
    private val excludeTaskUseCase: ExcludeTaskUseCase,
) : ViewModel() {

    private val _deleteTaskResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val deleteTaskResponse = _deleteTaskResponse.asStateFlow()

    private val _getTasksResponse = MutableStateFlow<ApiResponseWithData<List<TaskItem>>>(ApiResponseWithData.Default())
    val getTasksResponse: StateFlow<ApiResponseWithData<List<TaskItem>>> = _getTasksResponse

    var lastMonth = 0
    var lastYear = 0

    init {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

        getUserTasks(month, year, force = true)
    }

    fun logout() {
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }

    fun getUserTasks(month: Int, year: Int, force: Boolean = false) = viewModelScope.launch {
        if(lastMonth == month && lastYear == year && !force) return@launch
        lastMonth = month
        lastYear = year
        try {
            getUserTasksFromMonthUseCase.invoke(month, year, session.getToken()).collect {
                _getTasksResponse.value = it
            }
        } catch (e: Exception) {
            _getTasksResponse.value = ApiResponseWithData.DefaultError()
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
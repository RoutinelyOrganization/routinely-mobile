package com.routinely.routinely.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.core.useCase.LogoutUseCase
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.home.data.ExcludeTaskUseCase
import com.routinely.routinely.home.data.GetUserTasksFromMonthUseCase
import com.routinely.routinely.ui.components.Task
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import timber.log.Timber

class HomeViewModel(
    private val getUserTasksFromMonthUseCase: GetUserTasksFromMonthUseCase,
    private val excludeTaskUseCase: ExcludeTaskUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    private val _deleteTaskResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val deleteTaskResponse = _deleteTaskResponse.asStateFlow()
    private val _getTasksResponse =
        MutableStateFlow<ApiResponseWithData<List<TaskItem>>>(ApiResponseWithData.Default())
    val getTasksResponse: StateFlow<ApiResponseWithData<List<TaskItem>>> = _getTasksResponse
    var lastMonth = 0
    var lastYear = 0
    var lastDay = 0
    init {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        lastMonth = month
        lastYear = year
        lastDay = day
        getUserTasks(month, year, day)
    }
    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
    fun getUserTasks(month: Int, year: Int, day: Int, force: Boolean = false) =
        viewModelScope.launch {
            if (lastMonth == month && lastYear == year && lastDay == day && !force) return@launch
            lastMonth = month
            lastYear = year
            lastDay = day
            try {
                getUserTasksFromMonthUseCase.invoke(month, year, day, force).collect {
                    _getTasksResponse.value = it
                }
            } catch (e: Exception) {
                _getTasksResponse.value = ApiResponseWithData.DefaultError()
            }
        }
    fun excludeTask(task: TaskItem) = viewModelScope.launch {
        _deleteTaskResponse.value = excludeTaskUseCase(task.id)
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}

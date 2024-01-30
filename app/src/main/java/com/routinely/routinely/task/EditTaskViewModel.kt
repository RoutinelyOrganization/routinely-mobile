package com.routinely.routinely.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.task.data.GetTaskByIdUseCase
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditTaskViewModel(
    private val session: Session,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val taskApi: TaskApi
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val apiResponse = _apiResponse.asStateFlow()

    private val _task = MutableStateFlow<TaskItem?>(null)
    val task = _task.asStateFlow()

    fun saveTask(taskId: Int, newTask: TaskRequest) {
        val newTaskData = TaskRequest(
            name = newTask.name,
            date = newTask.date,
            hour = newTask.hour,
            description = newTask.description,
            accountId = session.getToken(),
            priority = newTask.priority,
            category = newTask.category,
            tag = newTask.tag,
        )

        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = taskApi.updateTask(taskId, newTaskData)
            } catch(e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = taskApi.excludeTask(taskId, session.getToken())
            } catch (e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
    }

    fun duplicateTask() {
        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = taskApi.addTask(
                    TaskRequest(
                        name = task.value!!.name,
                        date = task.value!!.date,
                        hour = task.value!!.hour,
                        description = task.value!!.description,
                        accountId = session.getToken(),
                        priority = task.value!!.priority.apiString,
                        category = task.value!!.category.apiString,
                        tag = task.value!!.tag.apiString,
                    )
                )
            } catch(e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
    }


    fun getTaskById(taskId: Int, month: Int, year: Int) {
        viewModelScope.launch {
            val response = getTaskByIdUseCase(taskId, month, year)
            _task.value = response
        }
    }

    fun taskNameState(taskName: String) : TaskNameInputValid {
        return when {
            taskName.isEmpty() -> {
                TaskNameInputValid.Error(R.string.empty_field)
            }
            taskName.count { it.isLetter() } > 50 -> {
                TaskNameInputValid.Error(R.string.task_name_limit)
            }
            else -> {
                TaskNameInputValid.Valid
            }
        }
    }

    fun taskDateState(taskDate: String) : DateTimeInputValid {
        return when {
            taskDate.isEmpty() -> {
                DateTimeInputValid.Error(R.string.empty_field)
            }
            else -> {
                DateTimeInputValid.Valid
            }
        }
    }

    fun taskTimeState(taskTime: String) : DateTimeInputValid {
        return when {
            taskTime.isEmpty() -> {
                DateTimeInputValid.Error(R.string.empty_field)
            }
            else -> {
                DateTimeInputValid.Valid
            }
        }
    }

    fun taskDescriptionState(description: String) : DescriptionInputValid {
        return when {
            description.isEmpty() -> {
                DescriptionInputValid.Error(R.string.empty_field)
            }
            else -> {
                DescriptionInputValid.Valid
            }
        }
    }

    fun logout() {
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }
}

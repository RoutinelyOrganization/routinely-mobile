package com.routinely.routinely.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.core.useCase.LogoutUseCase
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val taskApi: TaskApi,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val apiResponse = _apiResponse.asStateFlow()

    companion object {
        private const val TAG = "AddTaskViewModel"
    }

    fun addTask(newTask: TaskRequest) {
        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = taskApi.addTask(newTask)
            } catch (e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
    }

    fun taskNameState(taskName: String): TaskNameInputValid {
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

    fun taskDateState(taskDate: String): DateTimeInputValid {
        return when {
            taskDate.isEmpty() -> {
                DateTimeInputValid.Error(R.string.empty_field)
            }

            else -> {
                DateTimeInputValid.Valid
            }
        }
    }

    fun taskTimeState(taskTime: String): DateTimeInputValid {
        return when {
            taskTime.isEmpty() -> {
                DateTimeInputValid.Error(R.string.empty_field)
            }

            else -> {
                DateTimeInputValid.Valid
            }
        }
    }

    fun taskDescriptionState(description: String): DescriptionInputValid {
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
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

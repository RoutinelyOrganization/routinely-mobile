package com.routinely.routinely.task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.data.task.api.AddTaskApi
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.DropdownInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val addTaskApi: AddTaskApi,
    private val session: Session,
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val apiResponse = _apiResponse.asStateFlow()

    fun addTask(newTask: AddTaskRequest) {
        val newTaskData = AddTaskRequest(
            name = newTask.name,
            date = newTask.date,
            hour = newTask.hour,
            description = newTask.description,
            accountId = getTokenSession(),
            priority = newTask.priority,
            category = newTask.category,
            // TODO CHANGE AFTER BACKEND FIX THIS
            tag = newTask.category,
        )

        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = addTaskApi.addTask(newTaskData)
            } catch(e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
    }

    var shouldGoToNextScreen = mutableStateOf(false)
        private set
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

    fun taskCategoryState(category: String) : DropdownInputValid {
        return when {
            category.contains("Categorias") -> {
                DropdownInputValid.Error(R.string.empty_field)
            }
            else -> {
                DropdownInputValid.Valid
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

    private fun getTokenSession(): String {
            return session.getToken()
    }

    fun logout() {
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }
}
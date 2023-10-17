package com.routinely.routinely.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AddTaskApi
import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.DropdownInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskViewModel(
    private val addNewTaskApi: AddTaskApi,
) : ViewModel() {

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()
    private suspend fun addNewTask(newTask: AddTaskRequest): ApiResponse {
        return withContext(Dispatchers.IO) {
            val response: ApiResponse
            val newTaskData = AddTaskRequest(
                name = newTask.name,
                date = newTask.date,
                hour = newTask.hour,
                description = newTask.description,
                accountId = newTask.accountId,
                priority = newTask.priority,
                tag = newTask.tag
            )
            response = addNewTaskApi.addTask(newTaskData)
            return@withContext response
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

    fun taskPriorityState(priority: String) : DropdownInputValid {
        Log.d("priority", "taskPriorityState: " + priority)
        return when {
            priority.contains("Prioridade") -> {
                DropdownInputValid.Error(R.string.empty_field)
            }
            else -> {
                DropdownInputValid.Valid
            }
        }
    }

    fun taskTagState(tag: String) : DropdownInputValid {
        Log.d("tag", "taskTagState: " + tag)
        return when {
            tag.contains("Tags") -> {
                DropdownInputValid.Error(R.string.empty_field)
            }
            else -> {
                DropdownInputValid.Valid
            }
        }
    }

    fun taskCategoryState(category: String) : DropdownInputValid {
        Log.d("category", "taskCategoryState: " + category)
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

    fun verifyAllConditions(newTask: AddTaskRequest) {
        viewModelScope.launch {
            val response = addNewTask(newTask)
            if(response.serverStatusCode.value < 300) {
                shouldGoToNextScreen.value = true
            }else {
                _apiErrorMessage.value = response.message
            }
        }
    }
}
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
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditTaskViewModel(
    private val session: Session,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val taskApi: TaskApi
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiResponse>(ApiResponse.Empty)
    val apiResponse = _apiResponse.asStateFlow()

    private var task: TaskItem? = null

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
            } catch (e: Exception) {
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

    fun duplicateTask(): Boolean {
        if(task!!.name.contains("(5)")) return false

        val hourFormatter = DateTimeFormatter.ISO_DATE_TIME

        val dateTime = LocalDateTime.parse(task!!.hour, hourFormatter)
        val hour = dateTime.hour.toString()
        var minute = dateTime.minute.toString()
        if(minute.length == 1) {
            minute = "0$minute"
        }

        val name = duplicateItem(task!!.name)


        viewModelScope.launch {
            _apiResponse.value = ApiResponse.Loading
            try {
                _apiResponse.value = taskApi.addTask(
                    TaskRequest(
                        name = name,
                        date = task!!.date,
                        hour = "${hour}:${minute}",
                        description = task!!.description,
                        accountId = session.getToken(),
                        priority = task!!.priority.apiString,
                        category = task!!.category.apiString,
                        tag = task!!.tag.apiString,
                    )
                )
            } catch (e: Exception) {
                _apiResponse.value = ApiResponse.DefaultError
            }
        }
        return true
    }


    fun getTaskById(taskId: Int): TaskItem {
        return runBlocking {
            task = getTaskByIdUseCase(taskId)
            task!!
        }
    }

    private fun duplicateItem(name: String): String {
        val regex = """(.*)\s\((\d+)\)""".toRegex()
        val matchResult = regex.find(name)

        return if (matchResult != null) {
            val baseName = matchResult.groupValues[1]
            val number = matchResult.groupValues[2].toInt()
            "$baseName (${number + 1})"
        } else {
            "$name (1)"
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
        Log.d("HomeViewModel", "logout: Calling")
        viewModelScope.launch {
            session.setToken("")
            session.setRememberLogin(false)
        }
    }
}

package com.routinely.routinely.home.data

import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi,
) : GetUserTasksFromMonthUseCase {

    private var lastMonth = 0
    private var lastYear = 0
    private var lastResponseSuccess = false
    private val userTasks: MutableList<TaskItem> = mutableListOf()

    override suspend fun invoke(
        month: Int,
        year: Int,
        day: Int,
        force: Boolean,
    ): Flow<ApiResponseWithData<List<TaskItem>>> = flow {

        if (lastResponseSuccess && lastYear == year && lastMonth == month && !force) {
            val tasksByDay = getTasksByDayOfMonth(day, userTasks)
            emit(
                if(tasksByDay.isEmpty()) {
                    ApiResponseWithData.EmptyData()
                } else {
                    ApiResponseWithData.Success(tasksByDay)
                }
            )
        } else {
            lastMonth = month
            lastYear = year

            userTasks.clear()
            taskApi.getMonthTasks(month, year).collect {
                if (it::class == ApiResponseWithData.Success::class || it::class == ApiResponseWithData.EmptyData::class) {
                    lastResponseSuccess = true

                    if(it.data != null) {
                        userTasks.addAll(it.data)
                        val tasks = getTasksByDayOfMonth(day, userTasks)
                        emit(ApiResponseWithData.Success(tasks))
                    } else {
                        emit(ApiResponseWithData.EmptyData())
                    }
                } else {
                    lastResponseSuccess = false
                    emit(it)
                }
            }
        }
    }
}

private fun getTasksByDayOfMonth(day: Int, tasks: List<TaskItem>): List<TaskItem> {
    return tasks.filter { task ->
        val data = LocalDate.parse(task.date, DateTimeFormatter.ISO_DATE_TIME)
        data.dayOfMonth == day
    }
}


package com.routinely.routinely.home.data

import com.routinely.routinely.data.task.api.TaskApi
import com.routinely.routinely.util.ActionItem
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskItems
import com.routinely.routinely.util.TaskPriorities

class GetUserTasksFromMonthUseCaseImpl(
    private val taskApi: TaskApi
) : GetUserTasksFromMonthUseCase {
    override suspend fun invoke(month: Int, year: Int, userId: String): List<TaskItems> {
        return taskApi.getMonthTasks(month, year, userId).map {
            TaskItems(
                nameOfTask = it.name,
                category = mapApiStringToCategory(it.category),
                taskPriorities = mapApiStringToPriority(it.priority),
                listOfActions = listOf(
                    ActionItem.Edit(
                        onClick = {

                        }
                    ),
                    ActionItem.Exclude(
                        onClick = {

                        }
                    )
                )
            )
        }
    }


    private fun mapApiStringToCategory(apiString: String): TaskCategory {
        return when (apiString) {
            "career" -> TaskCategory.Career
            "personal" -> TaskCategory.Personal
            "health" -> TaskCategory.Health
            "finance" -> TaskCategory.Finances
            "study" -> TaskCategory.Studies
            else -> throw IllegalArgumentException("Unknown category: $apiString")
        }
    }

    private fun mapApiStringToPriority(apiString: String): TaskPriorities {
        return when (apiString) {
            "urgent" -> TaskPriorities.Urgent
            "high" -> TaskPriorities.High
            "medium" -> TaskPriorities.Medium
            "low" -> TaskPriorities.Low
            else -> throw IllegalArgumentException("Unknown priority: $apiString")
        }
    }
}
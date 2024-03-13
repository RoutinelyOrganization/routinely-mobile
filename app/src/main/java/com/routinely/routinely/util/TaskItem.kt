package com.routinely.routinely.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskItem(
    val id: Int,
    var name: String,
    var date: String,
    var hour: String,
    var tag: TaskTag,
    var priority: TaskPriorities,
    var category: TaskCategory,
    var description: String
) : Parcelable

data class TaskItemRemote(
    val id: Int,
    val name: String,
    val date: String,
    val hour: String,
    val tag: String,
    val priority: String,
    val category: String,
    val description: String
)
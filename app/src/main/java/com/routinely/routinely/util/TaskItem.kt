package com.routinely.routinely.util

import android.os.Parcelable
import com.routinely.routinely.ui.components.Task
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class TaskItem(
    val id: Int,
    val name: String,
    val description: String?,
    val category: String,
    val date: String,
    val type: String,
    val finallyDate: String?,
    val weekDays: List<String>,
) : Parcelable


package com.routinely.routinely.util

import com.routinely.routinely.R


sealed class BottomNavItems(
    var title: Int,
    var icon: Int
) {
    object Home:
            BottomNavItems(
                title = R.string.label_home_navigation_button,
                icon = R.drawable.home
            )
    object NewTask:
            BottomNavItems(
                title = R.string.label_new_task_navigation_button,
                icon = R.drawable.ic_new_task
            )
}
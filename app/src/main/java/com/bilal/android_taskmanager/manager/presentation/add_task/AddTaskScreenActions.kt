package com.bilal.android_taskmanager.manager.presentation.add_task

import com.bilal.android_taskmanager.manager.domain.TaskPriority
import java.util.Date

/**
 * Created by Bilal Hairab on 09/03/2025.
 */
sealed interface AddTaskScreenActions {
    data class CreateTaskAction(val title: String, val description: String?, val taskPriority: TaskPriority, val dueDate: Date): AddTaskScreenActions
}
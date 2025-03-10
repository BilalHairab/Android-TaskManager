package com.bilal.android_taskmanager.manager.presentation.list

import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import java.util.Date

/**
 * Created by Bilal Hairab on 09/03/2025.
 */
sealed interface TasksListScreenActions {
    data class LoadTasksAction(val lastID: Int): TasksListScreenActions
    data class OnTaskSelectedAction(val task: Task): TasksListScreenActions
}
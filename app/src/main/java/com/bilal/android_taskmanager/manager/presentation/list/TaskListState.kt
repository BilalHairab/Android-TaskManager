package com.bilal.android_taskmanager.manager.presentation.list

import androidx.compose.runtime.Immutable
import com.bilal.android_taskmanager.manager.domain.Task

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Immutable
data class TaskListState(
    val isLoading: Boolean = false,
    //The annotation because the read-only list is not always stable, so compose is not always capable to know if this is immutable or not (to avoid un-necessary recompose)
    val tasks: List<Task> = emptyList(),
    val selectedTask: Task? = null
)
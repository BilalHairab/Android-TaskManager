package com.bilal.android_taskmanager.manager.presentation.add_task

import androidx.compose.runtime.Immutable
import com.bilal.android_taskmanager.manager.domain.Error
import com.bilal.android_taskmanager.manager.domain.Task

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Immutable
data class AddTaskState(
    val isAdding: Boolean = false,
    val addingCompleted: Boolean = false,
    val error: Error? = null
)
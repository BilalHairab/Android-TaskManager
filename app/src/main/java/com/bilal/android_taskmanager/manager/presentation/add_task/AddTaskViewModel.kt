package com.bilal.android_taskmanager.manager.presentation.add_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import com.bilal.android_taskmanager.manager.domain.TasksDataSource
import com.bilal.android_taskmanager.manager.domain.onError
import com.bilal.android_taskmanager.manager.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

/**
 * Created by Bilal Hairab on 09/03/2025.
 */
class AddTaskViewModel(private val repository: TasksDataSource) : ViewModel() {
    private val _state = MutableStateFlow(AddTaskState())
    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AddTaskState())


    fun onAction(action: AddTaskScreenActions) {
        when (action) {
            is AddTaskScreenActions.CreateTaskAction -> {
                createTask(
                    title = action.title,
                    description = action.description,
                    taskPriority = action.taskPriority,
                    dueDate = action.dueDate
                )
            }
        }
    }

    private fun createTask(title: String, description: String?, taskPriority: TaskPriority, dueDate: Date) {
        viewModelScope.launch {
            _state.update {
                it.copy(isAdding = true)
            }

            repository.insertTask(Task(title = title, desc = description, priority = taskPriority, dueDate = dueDate)).onSuccess {
                Log.d("insertTask", "done")
                _state.update {
                    it.copy(isAdding = false, addingCompleted = true)
                }
            }.onError { error ->
                Log.e("insertTask", error.message)
                _state.update {
                    it.copy(isAdding = false, error = error)
                }
            }
        }
    }
}
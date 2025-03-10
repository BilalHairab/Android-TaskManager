package com.bilal.android_taskmanager.manager.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilal.android_taskmanager.manager.domain.TasksDataSource
import com.bilal.android_taskmanager.manager.domain.onError
import com.bilal.android_taskmanager.manager.domain.onSuccess
import com.bilal.android_taskmanager.manager.presentation.add_task.AddTaskScreenActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
class TasksListViewModel(private val repository: TasksDataSource): ViewModel() {
    private val _state = MutableStateFlow(TaskListState())
    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TaskListState())

    fun onAction(action: TasksListScreenActions) {
        when (action) {
            is TasksListScreenActions.LoadTasksAction -> {
                loadTasks(action.lastID)
            }

            is TasksListScreenActions.OnTaskSelectedAction -> {

            }
        }
    }

    private fun loadTasks(latestTaskID: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                repository.getNewTasks(latestTaskID).onSuccess { tasks ->
                    Log.d("TasksListViewModel", "latestTaskID $latestTaskID")
                    Log.d("TasksListViewModel", "tasks ${tasks.count()}")
                    _state.update { it.copy(isLoading = false, tasks = it.tasks + tasks) }
                }.onError { error ->
                    Log.d("TasksListViewModel", "tasks ${error.message}")
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
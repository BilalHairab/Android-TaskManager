package com.bilal.android_taskmanager.manager.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TasksDataSource
import com.bilal.android_taskmanager.manager.domain.onError
import com.bilal.android_taskmanager.manager.domain.onSuccess
import com.bilal.android_taskmanager.manager.presentation.models.TaskUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
class TasksListViewModel(private val repository: TasksDataSource) : ViewModel() {
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

            is TasksListScreenActions.ChangeTaskCompletenessAction -> {
                changeTaskCompleteness(action.task)
            }

            is TasksListScreenActions.ChangeTaskVisibilityAction -> {
                changeTaskCVisibility(task = action.task)
            }

            is TasksListScreenActions.DeleteTaskAction -> {
                deleteTask(action.task)
            }
        }
    }

    private fun changeTaskCompleteness(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newTask = task.copy(completed = true)
                repository.updateTask(newTask).onSuccess {
                    _state.update {
                        it.copy(tasks = it.tasks.map { t ->
                            if (t.task.id == task.id) {
                                t.copy(task = newTask)
                            } else
                                t
                        })
                    }
                }.onError {

                }
            }
        }
    }

    private fun changeTaskCVisibility(task: Task, visible: Boolean? = null) {
        viewModelScope.launch {
            _state.update {
                it.copy(tasks = it.tasks.map { t ->
                    if (t.task.id == task.id) {
                        Log.i("changeTaskCVisibility", "toVisibility ${visible ?: t.visible.not()}")
                        t.copy(visible = visible ?: t.visible.not())
                    } else
                        t
                })
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTask(task).onSuccess {
                    _state.update {
                        it.copy(tasks = it.tasks.dropLastWhile { t ->
                            t.task.id == task.id
                        })
                    }
                }.onError {
                    changeTaskCVisibility(task, true)
                }
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
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tasks = it.tasks + tasks.map { t -> TaskUi(task = t, visible = true) })
                    }
                }.onError { error ->
                    Log.d("TasksListViewModel", "tasks ${error.message}")
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
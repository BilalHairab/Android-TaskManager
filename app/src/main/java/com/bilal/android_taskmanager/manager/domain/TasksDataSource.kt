package com.bilal.android_taskmanager.manager.domain

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
interface TasksDataSource {
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    fun getAllTasks(): List<Task>

    suspend fun getTaskById(taskId: String): Task?
}
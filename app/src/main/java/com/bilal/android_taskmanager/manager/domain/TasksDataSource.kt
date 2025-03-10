package com.bilal.android_taskmanager.manager.domain

import com.bilal.android_taskmanager.manager.domain.Result

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
interface TasksDataSource {
    suspend fun insertTask(task: Task): Result<Unit, DBError>

    suspend fun updateTask(task: Task): Result<Unit, DBError>

    suspend fun deleteTask(task: Task): Result<Unit, DBError>

    suspend fun getNewTasks(taskID: Int): Result<List<Task>, DBError>

    suspend fun getTaskById(taskId: String): Result<Task?, DBError>
}
package com.bilal.android_taskmanager.manager.data.repos

import com.bilal.android_taskmanager.manager.data.room.TaskDao
import com.bilal.android_taskmanager.manager.domain.DBError
import com.bilal.android_taskmanager.manager.domain.Result
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TasksDataSource

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
class TaskRepository(private val taskDao: TaskDao) : TasksDataSource {
    override suspend fun insertTask(task: Task): Result<Unit, DBError> {
        return Result.Success(taskDao.insertTask(task))
    }

    override suspend fun updateTask(task: Task): Result<Unit, DBError> {
        return Result.Success(taskDao.updateTask(task))
    }

    override suspend fun deleteTask(task: Task): Result<Unit, DBError> {
        try {
            taskDao.deleteTask(task)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(DBError(e.message ?: "Unknown Error"))
        }
    }

    override fun getAllTasks(): Result<List<Task>, DBError> {
        return try {
            Result.Success(taskDao.getAllTasks())
        } catch (e: Exception) {
            Result.Error(DBError(e.message ?: "Unknown Error"))
        }
    }

    override suspend fun getTaskById(taskId: String): Result<Task?, DBError> {
        return try {
            Result.Success(taskDao.getTaskById(taskId))
        } catch (e: Exception) {
            Result.Error(DBError(e.message ?: "Unknown Error"))
        }
    }

}

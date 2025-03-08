package com.bilal.android_taskmanager.manager.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TasksDataSource

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Dao
interface TaskDao: TasksDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertTask(task: Task)

    @Update
    override suspend fun updateTask(task: Task)

    @Delete
    override suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    override fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    override suspend fun getTaskById(taskId: String): Task?
}
package com.bilal.android_taskmanager.manager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val desc: String? = null,
    val priority: TaskPriority,
    val dueDate: Date,
    val completed: Boolean = false
)
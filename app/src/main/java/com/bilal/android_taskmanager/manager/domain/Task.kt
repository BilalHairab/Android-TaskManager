package com.bilal.android_taskmanager.manager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String,
    val title: String,
    val desc: String?,
    val priority: TaskPriority,
    val dueDate: Date,
    val completed: Boolean
)
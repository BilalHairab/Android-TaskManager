package com.bilal.android_taskmanager.manager.data.converters

import androidx.room.TypeConverter
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import java.util.Date

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
class RoomConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }

    @TypeConverter
    fun priorityToString(priority: TaskPriority): String {
        return priority.name
    }
}
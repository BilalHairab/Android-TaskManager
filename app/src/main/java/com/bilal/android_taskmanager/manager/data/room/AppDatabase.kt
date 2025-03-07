package com.bilal.android_taskmanager.manager.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
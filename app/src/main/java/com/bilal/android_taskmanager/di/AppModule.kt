package com.bilal.android_taskmanager.di

import androidx.room.Room
import com.bilal.android_taskmanager.manager.data.room.AppDatabase
import org.koin.dsl.module

/**
 * Created by Bilal Hairab on 07/03/2025.
 */

val appModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "task_database")
            .build()
    }

    single { get<AppDatabase>().taskDao() }
}
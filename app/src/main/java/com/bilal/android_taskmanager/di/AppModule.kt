package com.bilal.android_taskmanager.di

import androidx.room.Room
import com.bilal.android_taskmanager.manager.data.repos.TaskRepository
import com.bilal.android_taskmanager.manager.data.room.AppDatabase
import com.bilal.android_taskmanager.manager.domain.TasksDataSource
import com.bilal.android_taskmanager.manager.presentation.add_task.AddTaskViewModel
import com.bilal.android_taskmanager.manager.presentation.list.TasksListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
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
    single<TasksDataSource> { TaskRepository(get()) }
    viewModelOf(::TasksListViewModel)
    viewModelOf(::AddTaskViewModel)
}
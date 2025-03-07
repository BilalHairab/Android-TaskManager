package com.bilal.android_taskmanager

import android.app.Application
import com.bilal.android_taskmanager.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Bilal Hairab on 08/03/2025.
 */
class TaskManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskManagerApp)
            androidLogger()
            modules(appModule)
        }
    }
}
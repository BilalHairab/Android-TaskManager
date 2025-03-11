package com.bilal.android_taskmanager.manager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bilal.android_taskmanager.manager.presentation.add_task.AddTaskScreen

/**
 * Created by Bilal Hairab on 09/03/2025.
 */

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { AdaptiveTaskListDetailPane(navController) }
        composable("add_task") { AddTaskScreen(navController) }
    }
}
package com.bilal.android_taskmanager.manager.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bilal.android_taskmanager.manager.presentation.navigation.NavigationGraph

/**
 * Created by Bilal Hairab on 09/03/2025.
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Box {
        NavigationGraph(navController)
    }
}
package com.bilal.android_taskmanager.manager.presentation.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bilal.android_taskmanager.manager.presentation.detail.TaskDetailScreen
import com.bilal.android_taskmanager.manager.presentation.list.TasksListScreen
import com.bilal.android_taskmanager.manager.presentation.list.TasksListScreenActions
import com.bilal.android_taskmanager.manager.presentation.list.TasksListViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Bilal Hairab on 27/10/2024.
 */

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveTaskListDetailPane(
    navController: NavController,
    viewModel: TasksListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                TasksListScreen(
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                TaskDetailScreen(
                    navController = navController,
                    task = state.selectedTask,
                    onUpdateTask = {
                        viewModel.onAction(TasksListScreenActions.ChangeTaskCompletenessAction(it))
                    },
                    onDeleteTask = {
                        viewModel.onAction(TasksListScreenActions.DeleteTaskAction(it))
                    }
                )
            }
        },
    )
}
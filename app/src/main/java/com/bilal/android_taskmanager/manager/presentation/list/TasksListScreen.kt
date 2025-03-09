package com.bilal.android_taskmanager.manager.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bilal.android_taskmanager.manager.presentation.list.components.TaskItemCard
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Bilal Hairab on 08/03/2025.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TasksListViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Manager") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("add_task") }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Task")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_task")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val state by viewModel.state.collectAsStateWithLifecycle()
            if (state.isLoading) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.tasks.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    items(state.tasks) { task ->
                        TaskItemCard(
                            task = task,
                            modifier = modifier.fillMaxWidth()
                        )
                        HorizontalDivider()
                    }
                }
            } else {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No Tasks added yet")
                }
            }
        }
    }
}
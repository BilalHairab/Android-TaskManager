package com.bilal.android_taskmanager.manager.presentation.list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bilal.android_taskmanager.manager.presentation.list.components.TaskItemCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var expandedSort by remember { mutableStateOf(false) }
    var expandedFilter by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Manager") },
                actions = {
                    Box {
                        IconButton(onClick = { expandedSort = true }) {
                            Icon(Icons.Default.Sort, contentDescription = "Sort")
                        }
                        DropdownMenu(expanded = expandedSort, onDismissRequest = { expandedSort = false }) {
                            DropdownMenuItem(
                                text = { Text("Sort by Priority") },
                                onClick = {
                                    viewModel.setSortOption(SortOption.PRIORITY)
                                    expandedSort = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Sort by Due Date") },
                                onClick = {
                                    viewModel.setSortOption(SortOption.DUE_DATE)
                                    expandedSort = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Sort Alphabetically") },
                                onClick = {
                                    viewModel.setSortOption(SortOption.ALPHABETICAL)
                                    expandedSort = false
                                }
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { expandedFilter = true }) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        }
                        DropdownMenu(expanded = expandedFilter, onDismissRequest = { expandedFilter = false }) {
                            DropdownMenuItem(
                                text = { Text("All Tasks") },
                                onClick = {
                                    viewModel.setFilterOption(FilterOption.ALL)
                                    expandedFilter = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Completed") },
                                onClick = {
                                    viewModel.setFilterOption(FilterOption.COMPLETED)
                                    expandedFilter = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Pending") },
                                onClick = {
                                    viewModel.setFilterOption(FilterOption.PENDING)
                                    expandedFilter = false
                                }
                            )
                        }
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
            val filteredTasks by viewModel.filteredTasks.collectAsStateWithLifecycle()
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                Log.d("TasksListViewModel", "DisposableEffect")
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        viewModel.onAction(TasksListScreenActions.LoadTasksAction(state.tasks.maxOfOrNull { it.task.id } ?: -1))
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

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
                    itemsIndexed(filteredTasks, key = {  _, task -> task.task.id }) { index, task ->
                        var isVisible by remember { mutableStateOf(false) } // للتحكم بظهور العنصر

                        LaunchedEffect(Unit) {
                            delay(index * 100L)
                            isVisible = true
                        }
                        AnimatedVisibility(
                            visible = isVisible,
                            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                            exit = fadeOut(),
                        ) {
                            TaskItemCard(
                                task = task.task,
                                modifier = modifier.fillMaxWidth(),
                                onDelete = {
                                    viewModel.onAction(TasksListScreenActions.ChangeTaskVisibilityAction(task.task))
                                    coroutineScope.launch {
                                        val result = snackBarHostState.showSnackbar(
                                            message = "${it.title} Task Deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onAction(TasksListScreenActions.ChangeTaskVisibilityAction(task.task))
                                        } else {
                                            viewModel.onAction(TasksListScreenActions.DeleteTaskAction(task.task))
                                        }
                                    }
                                },
                                onComplete = {
                                    viewModel.onAction(TasksListScreenActions.ChangeTaskCompletenessAction(task.task))
                                },
                            )
                        }
                    }
                }
            } else {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No Tasks added yet, add a new task to view here", textAlign = TextAlign.Center)
                }
            }
        }
    }
}
package com.bilal.android_taskmanager.manager.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bilal.android_taskmanager.manager.presentation.list.components.TaskItemCard
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Bilal Hairab on 08/03/2025.
 */

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksListViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if(state.tasks.isNotEmpty()){
        LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp))
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
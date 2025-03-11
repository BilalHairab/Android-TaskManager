package com.bilal.android_taskmanager.manager.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Bilal Hairab on 11/03/2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    task: Task?,
    onUpdateTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    if(task == null) {
        Box {  }
        return
    }
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.desc ?: "") }
    var priority by remember { mutableStateOf(task.priority) }
    var completed by remember { mutableStateOf(task.completed) }

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(task.dueDate)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Hide keyboard */ }),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Priority", style = MaterialTheme.typography.titleMedium)
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(priority.name)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    TaskPriority.entries.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                priority = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Due Date: $formattedDate",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Completed", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = completed, onCheckedChange = { completed = it })
            }

            Button(
                onClick = {
                    val updatedTask = task.copy(
                        title = title,
                        desc = description,
                        priority = priority,
                        completed = completed
                    )
                    onUpdateTask(updatedTask)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            OutlinedButton(
                onClick = {
                    onDeleteTask(task)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Delete Task")
            }
        }
    }
}

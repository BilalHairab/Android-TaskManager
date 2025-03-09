package com.bilal.android_taskmanager.manager.presentation.add_task

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import com.bilal.android_taskmanager.manager.presentation.models.getSurfaceColor
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Bilal Hairab on 09/03/2025.
 */

@Composable
fun AddTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.addingCompleted) {
        navController.popBackStack()
    }
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var priority by remember { mutableStateOf(TaskPriority.Low) } // Default priority

    var dueDate by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            dueDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Add New Task", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            val priorities = TaskPriority.entries.toTypedArray()

            Column {
                priorities.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (priority == item),
                                onClick = { priority = item },
                                role = Role.RadioButton
                            )
                            .background(item.getSurfaceColor())
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (priority == item),
                            onClick = { priority = item }
                        )
                        Text(text = item.name, modifier = Modifier.padding(start = 8.dp))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Due Date", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { datePickerDialog.show() },
                ) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Pick Date", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(dueDate?.let { dateFormat.format(it) } ?: "", textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    dueDate?.let {
                        viewModel.onAction(
                            AddTaskScreenActions.CreateTaskAction(
                                title.text, description.text, taskPriority = priority,
                                it
                            )
                        )
                    }
//                    onSave(title.text, description.text, priority)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = dueDate != null && title.text != "",
            ) {
                if (state.isAdding) {
                    CircularProgressIndicator()
                } else {
                    Text("Save Task")
                }
            }
        }
    }
}

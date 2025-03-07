package com.bilal.android_taskmanager.manager.presentation.list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bilal.android_taskmanager.R
import com.bilal.android_taskmanager.manager.domain.Task
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import com.bilal.android_taskmanager.manager.presentation.models.getSurfaceColor
import com.bilal.android_taskmanager.manager.presentation.models.toReadableDate
import com.bilal.android_taskmanager.ui.theme.AndroidTaskManagerTheme
import java.util.Date

/**
 * Created by Bilal Hairab on 07/03/2025.
 */
@Composable
fun TaskItemCard(
    task: Task,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    val titleTextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = contentColor
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(height = 80.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .shadow(
                elevation = 15.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.cardColors(
            containerColor = if(task.completed) MaterialTheme.colorScheme.surfaceContainer else task.priority.getSurfaceColor(),
            contentColor = contentColor
        )
    ) {
        Row(modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(id = if (task.completed) R.drawable.ic_completed else R.drawable.ic_pending),
                contentDescription = if (task.completed) "Completed" else "Incomplete",
                modifier = Modifier
                    .size(75.dp)
                    .padding(all = 2.dp)
                    .align(Alignment.CenterVertically),
                tint = contentColor
            )

            Column {
                Text(
                    text = task.title,
                    style = titleTextStyle,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = task.dueDate.toReadableDate(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TaskCardPreview() {
    AndroidTaskManagerTheme {
        TaskItemCard(
            task = Task(id = 1, title = "Review", dueDate = Date(), priority = TaskPriority.HIGH, completed = false),
        )
    }
}
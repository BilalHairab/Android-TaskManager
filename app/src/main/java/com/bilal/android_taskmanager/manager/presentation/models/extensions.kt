package com.bilal.android_taskmanager.manager.presentation.models

import androidx.compose.ui.graphics.Color
import com.bilal.android_taskmanager.manager.domain.TaskPriority
import java.util.Date

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Bilal Hairab on 07/03/2025.
 */

fun TaskPriority.getSurfaceColor(): Color {
    return if (this == TaskPriority.Low) {
        Color.Blue
    } else if (this == TaskPriority.Medium) {
        Color.Yellow
    } else {
        Color.Red
    }.copy(alpha = 0.3f)
}

fun Date.toReadableDate(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}

package com.bilal.android_taskmanager.manager.presentation.list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Bilal Hairab on 11/03/2025.
 */
@Composable
fun AnimatedCircularProgress(completedTasks: Int, totalTasks: Int) {
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = animatedProgress,
                strokeWidth = 8.dp,
                color = Color(0xFF38693C),
                trackColor = Color.LightGray,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
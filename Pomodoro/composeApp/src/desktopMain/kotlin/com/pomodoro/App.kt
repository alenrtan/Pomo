// contains ui code

package com.pomodoro

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun App() {
    MaterialTheme {
        val stopWatch = remember { StopWatch() }
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF29cc8d)) // the first hex pair is OPAQUENESS !!!
                .verticalScroll(rememberScrollState())
        )
        Column {
            StopWatchDisplay(
                formattedTime = stopWatch.formattedTime,
                onStartClick = stopWatch::start,
                onPauseClick = stopWatch::pause,
                onResetClick = stopWatch::reset
            )

            Spacer(modifier = Modifier.height(16.dp))
            CountdownTimer()

            GoalView()

        }
    }
}
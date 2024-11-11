// contains ui code

package com.pomodoro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.pomodoro.StopWatch
import com.pomodoro.StopWatchDisplay


import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val stopWatch = remember { StopWatch() }
        StopWatchDisplay(
            formattedTime = stopWatch.formattedTime,
            onStartClick = stopWatch::start,
            onPauseClick = stopWatch::pause,
            onResetClick = stopWatch::reset
        )
    }
}
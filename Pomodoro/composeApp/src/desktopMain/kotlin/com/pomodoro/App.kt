// contains ui code

package com.pomodoro

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun App() {
    MaterialTheme {

        var timerType by remember { mutableStateOf("None") } // lets the user choose timer or stopwatch

        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF29cc8d)), // the first hex pair is OPAQUENESS !!!
                contentAlignment = Alignment.Center
        ){
            // another box (container) for the centered timers
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                if (timerType == "None"){
                    // show choices
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Choose a timer", color = Color.Black, style = MaterialTheme.typography.h3)
                        Spacer(modifier = Modifier.height(15.dp))

                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Button(onClick = {timerType = "Stopwatch"}){
                                Text("Stopwatch")
                            }
                            Spacer(modifier = Modifier.padding(15.dp))
                            Button(onClick = {timerType = "Timer"}){
                                Text("Timer")
                            }
                        }

                    }
                }else{
                    // the user has already picked a timer - do logic based on it
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (timerType == "Stopwatch"){
                            val stopWatch = remember { StopWatch() }
                            Text("Stopwatch", color = Color.Black, style = MaterialTheme.typography.h3)
                            StopWatchDisplay(
                                formattedTime = stopWatch.formattedTime,
                                onStartClick = stopWatch::start,
                                onPauseClick = stopWatch::pause,
                                onResetClick = stopWatch::reset
                            )
                        }else{
                            Text("Timer", color = Color.Black, style = MaterialTheme.typography.h3)
                            CountdownTimer()
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(onClick = {timerType = "None"}){
                            Text("Back to menu")
                        }
                    }
                }
            }

            // another box (container) to keep the goals at the top left (like our prototype)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.TopStart
            ){
                GoalView()
            }
        }
    }
}
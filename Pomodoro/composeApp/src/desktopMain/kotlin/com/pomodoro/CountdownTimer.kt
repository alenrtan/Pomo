package com.pomodoro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun CountdownTimer() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var isRunning by remember { mutableStateOf(false) }
        var inputTime by remember { mutableStateOf("15")} // default value in minutes
        val timer = remember {Timer(inputTime.toInt() * 60)}

        // timer countdown
        LaunchedEffect(isRunning) {
            if (isRunning) {
                while (timer.timeFlow.value > 0 && timer.timerStatus) {
                    delay(1000)
                    timer.decrementTime()
                }
                isRunning = false // Automatically stop when timer reaches 0
            }
        }

        // begin page UI layout
        // add a box (kinda like a flex in html)
        Box (
            modifier = Modifier
                .background(Color(0xFF29cc8d)) // the first hex pair is OPAQUENESS !!!
        ){
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button( colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7ca696)),
                    onClick = {
                        showContent = !showContent

                        if (isRunning){
                            timer.stopTimer()
                        }else{
                            timer.startTimer()
                        }
                        isRunning = !isRunning
                    }) {
                    Text(if(isRunning) "Stop Timer" else "Set Timer") // text for the button
                }
                AnimatedVisibility(showContent){
                    Column( modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // allow the user to define timer
                        TextField(
                            value = inputTime,
                            onValueChange = { input ->
                                // input validation
                                val trimmedInput = input.trim().replace("\n", "")
                                if (trimmedInput.isNotEmpty() && trimmedInput.all { it.isDigit() }) {
                                    inputTime = trimmedInput // Only update if valid
                                }
                            },
                            label = { Text("Enter time in Minutes") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(200.dp)
                        )

                        Button(onClick = {
                            if (inputTime.isNotBlank() || inputTime.isNotEmpty()){
                                timer.resetTimer(inputTime.toInt() * 60)
                                isRunning = true
                            }
                        }){
                            Text("Set Time")
                        }

                        // showing the remaining time
                        val timeLeft by timer.timeFlow.collectAsState()
                        if (timeLeft > 0){
                            Text("Remaining time: ${formatTime(timeLeft)}")
                        }else{
                            Text("Times up! You currently don't have enough sessions for an insight. Keep Going!")
                            Button(onClick = {
                                showContent = !showContent
                                timer.resetTimer(10)
                            }) {
                                Text("Reset")
                            }
                        }

                    }
                }
            }
        }

    } // end MaterialTheme
} // end app


class Timer(startTime: Int) {
    // initial variables
    private var timeLeft = MutableStateFlow(startTime) // holds the state/status of the remaining time
    var timeFlow = timeLeft.asStateFlow()
    var timerStatus = false // running status

    // https://kotlinlang.org/docs/composing-suspending-functions.html#sequential-by-default
    // kinda like threading? but not?
//    suspend fun startTimer(){
//        timerStatus = true // timer running
//        while(timeLeft.value > 0){
//            delay(1000)
//            timeLeft.value--
//        }
//
//        timerStatus = false;
//    }

    fun startTimer() {
        timerStatus = true
    }

    fun stopTimer(){
        timerStatus = false
    }

    fun resetTimer(newTime: Int){
        timeLeft.value = newTime
    }

    fun decrementTime(){
        timeLeft.value--
    }

} //end Timer class

// function to help show time correctly from milli
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds);
} //end formatTime
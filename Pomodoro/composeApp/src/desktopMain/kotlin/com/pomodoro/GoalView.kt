package com.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@Composable
fun GoalView() {
    var task by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<String>()) }

    Column (
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Goals",
            style = TextStyle(fontSize = 25.sp ,fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        )

        tasks.forEach { task ->
            Text(
                task,
                style = MaterialTheme.typography.body1
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = task,
                onValueChange = { task = it},
                label = { Text("enter task") }
            )

            Button(onClick = {
                if (task.isNotBlank()) {
                    tasks = tasks + task
                    print("added $task")
                    task = ""
                }
            }) {
                Text("Add task")
            }
        } //Column
    } //Column
} //GoalView

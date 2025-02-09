package com.example.language_tutor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.language_tutor.ui.components.LevelButton
import com.example.language_tutor.ui.components.ActionButton
import com.example.language_tutor.ui.components.SectionButton

@Composable
fun ExamScreen(onStartTest: (String) -> Unit) {
    var selectedLevel by remember { mutableStateOf<String?>(null) }
    var selectedSection by remember { mutableStateOf<String?>(null) }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Выберите уровень", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    listOf("A1", "A2", "B1", "B2").forEach { level ->
                        LevelButton(level, selectedLevel) { selectedLevel = it }
                    }
                }

                ActionButton(
                    text = "Начать",
                    onClick = {
                        selectedLevel?.let { level ->
                            onStartTest(level)
                        }
                    }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Mock IELTS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Выберите секции", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

                LazyRow(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    items(listOf("Reading", "Listening", "Writing")) { section ->
                        SectionButton(section, selectedSection) { selectedSection = it }
                    }
                }

                ActionButton(text = "Начать", onClick = { /* TODO: Implement IELTS test navigation */ })
            }
        }
    }
}
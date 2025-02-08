package com.example.examscreen


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LanguageTestScreen()
            }
        }
    }
}

@Composable
fun LanguageTestScreen() {
    var selectedLevel by remember { mutableStateOf<String?>(null) }
    var selectedSection by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Выберите уровень", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("A1", "A2", "B1", "B2").forEach { level ->
                LevelButton(level, selectedLevel) { selectedLevel = it }
            }
        }

        ActionButton(text = "Начать", onClick = { /* Позже добавим обработку */ })

        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp, color = Color.Gray)

        Text("Mock IELTS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Выберите секции", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Reading", "Listening", "Writing").forEach { section ->
                SectionButton(section, selectedSection) { selectedSection = it }
            }
        }

        ActionButton(text = "Начать", onClick = { /* Позже добавим обработку */ })
    }
}

@Composable
fun LevelButton(level: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(level) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == level) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(64.dp)
    ) {
        Text(level, color = Color.White)
    }
}

@Composable
fun SectionButton(section: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(section) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == section) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Text(section, color = Color.White)
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, color = Color.White)
    }
}

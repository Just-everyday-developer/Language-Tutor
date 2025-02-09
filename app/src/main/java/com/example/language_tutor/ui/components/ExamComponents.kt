package com.example.language_tutor.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.LevelButton(level: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(level) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == level) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp).weight(1f)
    ) {
        Text(level, color = Color.White, maxLines = 1)
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
        modifier = Modifier.width(105.dp)
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
// ui/screens/CoursesScreen.kt
package com.example.language_tutor.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.language_tutor.R
import com.example.language_tutor.R.drawable.*
import com.example.language_tutor.ui.components.CourseCard
import com.example.language_tutor.ui.components.Search

@Composable
fun CoursesScreen() {
    val coursesList = listOf(
        "Английский для начинающих",
        "Английский для продолжающих",
        "Подготовка к IELTS"
    )
    val prices = listOf("Бесплатно", "60$", "180$")
    val photos = listOf(
        R.drawable.for_newbies,
        R.drawable.for_intermediaters,
        R.drawable.for_ielts
    )

    Column {
        Search(Modifier.padding(bottom = 20.dp))

        LazyColumn(Modifier.fillMaxSize()) {
            items(listOf(0, 1, 2)) { index ->
                CourseCard(
                    courseName = coursesList[index],
                    price = prices[index],
                    photoRes = photos[index]
                )
            }
        }
    }
}
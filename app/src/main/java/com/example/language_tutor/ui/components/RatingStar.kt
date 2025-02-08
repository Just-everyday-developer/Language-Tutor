// ui/components/RatingStar.kt
package com.example.language_tutor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RatingStar(
    rating: Float = 5f,
    maxRating: Int = 5,
    onStarClick: (Int) -> Unit,
    isIndicator: Boolean = false
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= rating.toInt()) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = !isIndicator) { onStarClick(i) }
                )
            } else if (i == rating.toInt() + 1 && rating % 1 != 0f) {
                PartialStar(fraction = rating % 1)
            } else {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = !isIndicator) { onStarClick(i) }
                )
            }
        }
    }
}
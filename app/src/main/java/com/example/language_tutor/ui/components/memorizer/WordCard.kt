// ui/components/memorizer/WordCard.kt
package com.example.language_tutor.ui.components.memorizer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.language_tutor.data.Word
import com.example.language_tutor.viewmodel.WordViewModel

@Composable
fun WordCard(word: Word, viewModel: WordViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (word.isLearned)
                MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = word.word,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Перевод: ${word.translation}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Уровень: ${word.level}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.toggleLearnedStatus(word) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (word.isLearned)
                            MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (word.isLearned) "Не выучено" else "Выучил")
                }

                Button(
                    onClick = { viewModel.deleteWord(word) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}
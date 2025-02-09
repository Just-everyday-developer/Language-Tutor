package com.example.language_tutor.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun DrawerItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        selected = false,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = text
            )
        },
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun AppDrawer(
    onDestinationClicked: (route: String) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp)
    ) {
        Text(
            text = "Меню",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(24.dp),
            fontWeight = FontWeight.Bold
        )
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )

        DrawerItem("Главная", Icons.Filled.Menu) { onDestinationClicked("main") }
        DrawerItem("Моя статистика", Icons.Filled.Star) { onDestinationClicked("statistics") }
        DrawerItem("Экзамен", Icons.Filled.Star) { onDestinationClicked("exam") }
        DrawerItem("Запоминатор", Icons.Rounded.Search) { onDestinationClicked("memorizer") }

    }

}
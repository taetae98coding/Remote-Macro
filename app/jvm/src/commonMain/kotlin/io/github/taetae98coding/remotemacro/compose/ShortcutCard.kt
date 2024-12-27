package io.github.taetae98coding.remotemacro.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShortcutCard(
    modifier: Modifier = Modifier
) {
    Card(modifier=modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Shortcut",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            Text("Setting : F1")
            Text("Start/Stop : Spacebar")
        }
    }
}
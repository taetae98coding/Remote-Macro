package io.github.taetae98coding.remotemacro.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MacroButton(
    state: MacroState,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = {
            if (state.isActive) {
                state.stop()
            } else {
                state.start()
            }
        },
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Crossfade(
            targetState = state.isActive,
            modifier = Modifier.fillMaxSize()
        ) { isActive ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isActive) {
                        "STOP"
                    } else {
                        "START"
                    },
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

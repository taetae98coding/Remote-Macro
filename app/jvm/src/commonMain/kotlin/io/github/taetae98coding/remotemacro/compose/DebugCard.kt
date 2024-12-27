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
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Composable
fun DebugCard(
    state: MacroState,
    durationRangeProvider: () -> ClosedRange<Duration>,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            val duration = durationRangeProvider()

            Text(
                text = "Debug",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Active : ${state.isActive}",
                maxLines = 1,
            )

            Text(text = "Move mouse delay seconds(${duration.start.toInt(DurationUnit.SECONDS)} ~ ${duration.endInclusive.toInt(DurationUnit.SECONDS)})")
        }
    }
}
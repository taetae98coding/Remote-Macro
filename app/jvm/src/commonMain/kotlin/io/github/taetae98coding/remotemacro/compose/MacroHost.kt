package io.github.taetae98coding.remotemacro.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

@Composable
fun MacroHost(
    state: MacroState,
    durationRangeProvider: () -> ClosedRange<Duration>,
) {
    LaunchedEffect(state.isActive) {
        while (state.isActive) {
            val range = durationRangeProvider()
            val start = range.start.toLong(DurationUnit.MILLISECONDS)
            val endInclusive = range.endInclusive.toLong(DurationUnit.MILLISECONDS)
            val delay = Random.nextLong(start..endInclusive)

            state.moveMouse()
            delay(delay.milliseconds)
        }
    }
}
package io.github.taetae98coding.remotemacro

import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import androidx.lifecycle.compose.LifecycleStartEffect
import io.github.taetae98coding.remotemacro.compose.DebugCard
import io.github.taetae98coding.remotemacro.compose.LogColumn
import io.github.taetae98coding.remotemacro.compose.MacroButton
import io.github.taetae98coding.remotemacro.compose.MacroHost
import io.github.taetae98coding.remotemacro.compose.MacroSettingBottomSheet
import io.github.taetae98coding.remotemacro.compose.MacroState
import io.github.taetae98coding.remotemacro.compose.ShortcutCard
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun main() {
    val state = WindowState()

    singleWindowApplication(
        state = state,
        title = "Remote Macro",
    ) {
        val density = LocalDensity.current
        val macroState = remember(density, state) {
            MacroState(density, state)
        }

        App(state = macroState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun App(
    state: MacroState,
) {
    val colorScheme = if (isSystemInDarkTheme()) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        val focusRequester = remember { FocusRequester() }
        var isSettingVisible by rememberSaveable { mutableStateOf(true) }
        var startSeconds by rememberSaveable { mutableStateOf(150) }
        var endInclusiveSeconds by rememberSaveable { mutableStateOf(150) }

        Scaffold(
            modifier = Modifier
                .onPreviewKeyEvent { event ->
                    when {
                        event.key == Key.Spacebar && event.type == KeyEventType.KeyUp -> {
                            if (state.isActive) {
                                state.stop()
                            } else {
                                state.start()
                            }
                            true
                        }

                        event.key == Key.F1 && event.type == KeyEventType.KeyUp -> {
                            state.stop()
                            isSettingVisible = true
                            true
                        }

                        else -> false
                    }
                }
                .focusRequester(focusRequester)
                .focusable(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Remote Macro") },
                    actions = {
                        IconButton(
                            onClick = {
                                state.stop()
                                isSettingVisible = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            Content(
                state = state,
                durationRangeProvider = { startSeconds.seconds..endInclusiveSeconds.seconds },
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .padding(8.dp),
            )
        }

        if (isSettingVisible) {
            SettingBottomSheet(
                onDismissRequest = { isSettingVisible = false },
                durationRangeProvider = { startSeconds.seconds..endInclusiveSeconds.seconds },
                onDurationChange = {
                    startSeconds = it.start.toInt(DurationUnit.SECONDS)
                    endInclusiveSeconds = it.endInclusive.toInt(DurationUnit.SECONDS)
                },
                modifier = Modifier
                    .onPreviewKeyEvent { event ->
                        when {
                            event.key == Key.Spacebar && event.type == KeyEventType.KeyUp -> {
                                isSettingVisible = false
                                if (state.isActive) {
                                    state.stop()
                                } else {
                                    state.start()
                                }
                                true
                            }

                            event.key == Key.F1 && event.type == KeyEventType.KeyUp -> {
                                state.stop()
                                isSettingVisible = true
                                true
                            }

                            else -> false
                        }
                    }
                    .focusRequester(focusRequester)
                    .focusable(),
            )

            LifecycleStartEffect(focusRequester) {
                focusRequester.requestFocus()
                onStopOrDispose {}
            }
        }

        MacroHost(
            state = state,
            durationRangeProvider = { startSeconds.seconds..endInclusiveSeconds.seconds },
        )

        LifecycleStartEffect(focusRequester) {
            focusRequester.requestFocus()
            onStopOrDispose {}
        }
    }
}

@Composable
private fun Content(
    state: MacroState,
    durationRangeProvider: () -> ClosedRange<Duration>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DebugCard(
            state = state,
            durationRangeProvider = durationRangeProvider,
            modifier = Modifier.fillMaxWidth(),
        )
        ShortcutCard(
            modifier = Modifier.fillMaxWidth(),
        )
        LogColumn(
            state = state,
            modifier = Modifier.fillMaxWidth()
                .weight(1F)
        )
        MacroButton(
            state = state,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingBottomSheet(
    onDismissRequest: () -> Unit,
    durationRangeProvider: () -> ClosedRange<Duration>,
    onDurationChange: (ClosedRange<Duration>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val duration = durationRangeProvider()
    val state = remember {
        RangeSliderState(
            activeRangeStart = duration.start.toInt(DurationUnit.SECONDS).toFloat(),
            activeRangeEnd = duration.endInclusive.toInt(DurationUnit.SECONDS).toFloat(),
            valueRange = 0F..300F,
        )
    }

    MacroSettingBottomSheet(
        onDismissRequest = onDismissRequest,
        state = state,
        modifier = modifier,
    )

    LaunchedEffect(state.activeRangeStart, state.activeRangeEnd) {
        onDurationChange(state.activeRangeStart.toInt().seconds..state.activeRangeEnd.toInt().seconds)
    }
}

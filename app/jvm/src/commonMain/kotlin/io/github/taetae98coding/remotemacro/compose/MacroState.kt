package io.github.taetae98coding.remotemacro.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.WindowState
import io.github.taetae98coding.remotemacro.entity.LogMessage
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.awt.Robot
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MacroState(
    private val density: Density,
    private val windowState: WindowState,
) {
    private val robot = Robot()

    var isActive by mutableStateOf(false)
        private set

    var messageList by mutableStateOf(emptyList<LogMessage>())
        private set

    fun start() {
        if (isActive) return

        isActive = true
        messageList = buildList {
            val message = LogMessage(
                key = Uuid.random().toString(),
                message = "start",
                dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            addAll(messageList)
            add(message)
        }
    }

    fun stop() {
        if (!isActive) return

        isActive = false
        messageList = buildList {
            val message = LogMessage(
                key = Uuid.random().toString(),
                message = "stop",
                dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            addAll(messageList)
            add(message)
        }
    }

    fun moveMouse() {
        val x = with(density) {
            val start = windowState.position.x.roundToPx()
            val endInclusive = (windowState.position.x + windowState.size.width).roundToPx()

            Random.nextInt(start..endInclusive)
        }
        val y = with(density) {
            val start = windowState.position.y.roundToPx()
            val endInclusive = (windowState.position.y + windowState.size.height).roundToPx()

            Random.nextInt(start..endInclusive)
        }

        robot.mouseMove(x, y)
        messageList = buildList {
            val message = LogMessage(
                key = Uuid.random().toString(),
                message = "move mouse($x, $y)",
                dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            addAll(messageList)
            add(message)
        }
    }
}
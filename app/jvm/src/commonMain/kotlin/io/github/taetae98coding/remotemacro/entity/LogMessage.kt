package io.github.taetae98coding.remotemacro.entity

import kotlinx.datetime.LocalDateTime

data class LogMessage(
    val key: String,
    val message: String,
    val dateTime: LocalDateTime
)
package no.uib.echo.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDate(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun getSystemTZ(): TimeZone = TimeZone.currentSystemDefault()

package com.wsafight.findtime

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TimeZoneHelperImpl: TimeZoneHelper {
    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun currentTime(): String {
        val currentMoment = Clock.System.now()
        val dateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return formatDateTime(dateTime)
    }

    override fun currentTimeZone(): String {
        val currentTimeZone = TimeZone.currentSystemDefault()
        return currentTimeZone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {
        val currentUTCInstant = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        val otherTimeZone = TimeZone.of(otherTimeZoneId)
        val currentDateTime = currentUTCInstant.toLocalDateTime(currentTimeZone)
        val otherDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        return abs((currentDateTime.hour - otherDateTime.hour) * 1.0)
    }

    override fun getTime(timeZoneId: String): String {
        val timeZone = TimeZone.of(timeZoneId)
        var currentMoment = Clock.System.now()
        val dateTime = currentMoment.toLocalDateTime(timeZone)
        return formatDateTime(dateTime)
    }

    override fun getDate(timeZoneId: String): String {
        val timeZone = TimeZone.of(timeZoneId)
        val currentMoment = Clock.System.now()
        val dateTime = currentMoment.toLocalDateTime(timeZone)
        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, ${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.date.dayOfMonth}"
    }

    override fun search(startHour: Int, endHour: Int, timeZoneStringList: List<String>): List<Int> {
        val goodHours = mutableListOf<Int>()
        val timeRange = IntRange(max(0, startHour), min(23, endHour))
        val currentTimeZone = TimeZone.currentSystemDefault()
        for (hour in timeRange) {
            var isGoodHour = false
            for (zone in timeZoneStringList) {
                val timeZone = TimeZone.of(zone)
                if (timeZone == currentTimeZone) {
                    continue
                }
                if (!isValid(
                        timeRange,
                        hour,
                        currentTimeZone,
                        timeZone
                )){
                    Napier.d("Hour $hour is not valid for time range")
                    isGoodHour = false
                    break
                } else {
                    Napier.d("Hour $hour is Valid for time range")
                    isGoodHour = true
                }
            }
            if (isGoodHour) {
                goodHours.add(hour)
            }
        }
        return goodHours
    }

    private fun isValid(
        timeRange: IntRange,
        hour: Int,
        currentTimeZone: TimeZone,
        otherTimeZone: TimeZone
    ): Boolean {
        if (hour !in timeRange) {
            return false
        }
        val currentUTCInstant = Clock.System.now()
        val otherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        val otherDateTimeWithHour = LocalDateTime(
            otherDateTime.year,
            otherDateTime.monthNumber,
            otherDateTime.dayOfMonth,
            hour,
            0,
            0,
            0
        )
        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")
        return convertedTime.hour in timeRange
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        var hour = dateTime.hour % 12
        if (hour == 0) {
            hour = 12
        }
        val amPm = if (hour < 12) "am" else "pm"
        val minute = dateTime.minute
        val minutePrefix  = if(minute < 10) "0" else ""
        return "$hour:$minutePrefix$minute $amPm"
    }
}
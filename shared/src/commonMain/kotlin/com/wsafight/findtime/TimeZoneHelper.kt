package com.wsafight.findtime

interface TimeZoneHelper {
    fun getTimeZoneStrings(): List<String>
    fun currentTime(): String
    fun currentTimeZone(): String
    fun hoursFromTimeZone(otherTimeZoneId: String): Double
    fun getTime(timeZoneId: String): String
    fun getDate(timeZoneId: String): String
    fun search(startHour: Int, endHour: Int,timeZoneStringList: List<String>): List<Int>
}
package com.wsafight.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wsafight.findtime.TimeZoneHelperImpl
import kotlinx.coroutines.delay

// 一分钟
const val timeMillis = 1000 * 60L

@Composable
fun TimeZoneScreen(
    currentTimeZoneStrings: SnapshotStateList<String>
) {
    val timeZoneHelper = TimeZoneHelperImpl()
    val listState = rememberLazyListState()

    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        var time by remember {
            mutableStateOf(timeZoneHelper.currentTime())
        }

        LaunchedEffect(Unit) {
            while (true) {
                time = timeZoneHelper.currentTime()
                delay(timeMillis)
            }
        }
        LocalTimeCard(
            city = timeZoneHelper.currentTimeZone(),
            time = time,
            date = timeZoneHelper.getDate(timeZoneHelper.currentTimeZone())
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            state = listState,
        ) {
            items(currentTimeZoneStrings.size,
                key = { timezone ->
                    timezone
                }) { index ->
                val timezoneString = currentTimeZoneStrings[index]
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                )
                        ) {
                            val alpha = 1f
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    content = {
                        TimeCard(
                            timeZone = timezoneString,
                            hours = timeZoneHelper.hoursFromTimeZone(timezoneString),
                            time = timeZoneHelper.getTime(timezoneString),
                            date = timeZoneHelper.getDate(timezoneString)
                        )
                    },
                    onDismiss = { zone ->
                        if (currentTimeZoneStrings.contains(zone)) {
                            currentTimeZoneStrings.remove(zone)
                        }
                        println("currentTimeZoneStrings is ${currentTimeZoneStrings.toList()}")
                    },
                )
            }
        }
    }
}
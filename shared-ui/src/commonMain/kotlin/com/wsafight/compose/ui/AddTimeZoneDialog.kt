package com.wsafight.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wsafight.findtime.TimeZoneHelper
import com.wsafight.findtime.TimeZoneHelperImpl
import kotlinx.coroutines.launch

@Composable
expect fun AddTimeDialogWrapper(
    onDismiss: onDismissType,
    content: @Composable () -> Unit
)

fun isSelected(
    selectedStates: Map<Int, Boolean>,
    index: Int
): Boolean {
    return selectedStates.containsKey(index) && (true == selectedStates[index])
}


@Composable
fun AddTimeZoneDialog(
    onAdd: (newTimeZones: List<String>) -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    onDismissRequest = onDismiss
) {
    val timeZoneHelper: TimeZoneHelper = TimeZoneHelperImpl()

    AddTimeDialogWrapper(onDismiss) {
        Surface(
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                val timeZoneStrings by remember {
                    mutableStateOf(
                        timeZoneHelper.getTimeZoneStrings().toList()
                    )
                }
                val selectedStates = remember { SnapshotStateMap<Int, Boolean>() }
                val listState = rememberLazyListState()
                val searchValue = remember { mutableStateOf("") }
                val coroutineScope = rememberCoroutineScope()
                val focusRequester = remember { FocusRequester() }

                OutlinedTextField(
                    singleLine = true,
                    value = searchValue.value,
                    modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
                    onValueChange = {
                        searchValue.value = it
                        if (searchValue.value.isEmpty()) {
                            return@OutlinedTextField
                        }
                        val index = searchZones(searchValue.value, timeZoneStrings)
                        if (-1 != index) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                searchValue.value = ""
                            }
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "Cancel"
                            )
                        }
                    }
                )
                DisposableEffect(Unit) {
                    focusRequester.requestFocus()
                    onDispose { }
                }
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentPadding = PaddingValues(16.dp),
                    state = listState
                ) {
                    itemsIndexed(timeZoneStrings) { i, timeZone ->
                        Surface(
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                            color = if (isSelected(selectedStates, i))
                                MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                        ) {
                            Row(
                                modifier = Modifier
                                    .toggleable(
                                        value = isSelected(selectedStates, i),
                                        onValueChange = {
                                            selectedStates[i] = it
                                        }
                                    ).fillMaxWidth(),
                            ) {
                                Text(timeZone)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = {
                            onAdd(
                                getTimeZones(
                                    selectedStates,
                                    timeZoneStrings,
                                )
                            )
                        }
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

fun searchZones(searchString: String, timeZoneStrings:List<String>): Int {
    var timeZone = timeZoneStrings.firstOrNull { it.startsWith(searchString, ignoreCase = true) }
    if (null == timeZone) {
        timeZone = timeZoneStrings.firstOrNull { it.contains(searchString, ignoreCase = true) }
    }
    if (null != timeZone) {
        return timeZoneStrings.indexOf(timeZone)
    }

    return -1
}

fun getTimeZones(selectedStates: Map<Int, Boolean>, timeZoneStrings: List<String>): List<String> {
    val timeZoneIndexes = selectedStates.map { if (it.value) it.key else -1 }
    val timeZones = mutableListOf<String>()
    timeZoneIndexes.forEach { index ->
        if (-1 != index) {
            timeZones.add(timeZoneStrings[index])
        }
    }
    return timeZones
}
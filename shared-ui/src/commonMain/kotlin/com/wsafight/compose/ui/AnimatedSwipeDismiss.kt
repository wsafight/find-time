package com.wsafight.compose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T>  AnimatedSwipeDismiss(
    modifier: Modifier = Modifier,
    item: T,
    background: @Composable RowScope.()  -> Unit,
    content: @Composable RowScope.() -> Unit,
    onDismiss: (T) -> Unit,
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(
        animationSpec = tween(
            durationMillis = 500,
        )
    ),
) {

    val visible = remember { mutableStateOf(true) }

    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                visible.value = false
                onDismiss(item)
            }
            return@rememberSwipeToDismissBoxState true
        }
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = visible.value,
        enter = enter,
        exit = exit
    ) {
        SwipeToDismissBox(
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            state = swipeState,
            backgroundContent = background,
            modifier = modifier,
            gesturesEnabled = true,
            content = content
        )
    }


}



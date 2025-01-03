package com.wsafight.compose.ui

import androidx.compose.runtime.Composable

typealias OnAddType =  (List<String>) -> Unit
typealias onDismissType =  () -> Unit
typealias composeFun =  @Composable () -> Unit
typealias topBarFun =  @Composable (Int) -> Unit

@Composable
fun EmptyComposable() {
}
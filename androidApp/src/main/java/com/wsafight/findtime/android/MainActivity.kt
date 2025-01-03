package com.wsafight.findtime.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wsafight.compose.ui.MainView
import com.wsafight.compose.ui.MyApplicationTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        setContent {
            MainView() {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    title = {
                        when (it) {
                            0 -> Text("World Clocks")
                            else -> Text("Find Meeting Time")
                        }
                    })
            }
        }
    }
}


@Composable
fun showName(text: String) {
    Text(text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        showName("Hello, Android!")
    }
}

package com.wsafight.findtime.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import com.wsafight.findtime.android.ui.MainView

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        setContent {
            MainView {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    title = {
                        when (it) {
                            0 -> Text(text = stringResource(R.string.world_clocks))
                            else -> Text(text = stringResource(R.string.findmeeting))
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

package com.wsafight.findtime.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wsafight.findtime.android.endGradientColor
import com.wsafight.findtime.android.startGradientColor

@Composable
fun LocalTimeCard(
    city: String,
    time: String,
    date: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            startGradientColor,
                            endGradientColor
                        )
                    )
                ).padding(8.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            "Your Location",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(city, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Column (
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            time, style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            date, style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
package com.example.threecounters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.threecounters.ui.theme.ThreeCountersTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    Threecounterscreen()
                }
            }
        }
    }
}

@Composable
fun CounterRow(label: String){
    var counterText by remember {mutableStateOf(value = "0")}

    val countervalue = counterText.toIntOrNull() ?:0

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ){
        Text(
            text = label,
            modifier = Modifier.width(width = 90.dp)
        )
        Button(
            onClick = {
            counterText = (countervalue - 1).toString()
            }

        ){
            Text(text = "-")
        }
        TextField(
            value = counterText,
            onValueChange = {
                newText ->
                counterText = newText
            },
            modifier = Modifier.width(width = 90.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {Text(text = "start")}
        )
        Button(
            onClick = {
                counterText = (countervalue + 1).toString()
            }

        ){
            Text(text = "+")
        }
    }
}
@Composable
fun Threecounterscreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CounterRow("1st")
            CounterRow("2nd")
            CounterRow("3rd")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Surface {
            Threecounterscreen()
        }
    }
}
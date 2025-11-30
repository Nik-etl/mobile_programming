package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// DataStore keys and instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")
val FONT_SIZE_KEY = intPreferencesKey("font_size")
val FONT_WEIGHT_KEY = stringPreferencesKey("font_weight")
val FONT_STYLE_KEY = stringPreferencesKey("font_style")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorApp()
        }
    }
}

@Composable
fun SensorApp() {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
    val coroutineScope = rememberCoroutineScope()

    // Sensor data states
    var accelerometerData by remember { mutableStateOf("Accelerometer: N/A") }
    var gyroscopeData by remember { mutableStateOf("Gyroscope: N/A") }
    var magnetometerData by remember { mutableStateOf("Magnetometer: N/A") }
    var temperatureData by remember { mutableStateOf("Temperature: N/A") }
    var luxData by remember { mutableStateOf("Light (Lux): N/A") }

    // Font preferences from DataStore
    val fontSizeFlow = context.dataStore.data.map { it[FONT_SIZE_KEY] ?: 16 }
    val fontSize by produceState(initialValue = 16, key1 = fontSizeFlow) {
        value = fontSizeFlow.first()
    }

    val fontWeightFlow = context.dataStore.data.map { it[FONT_WEIGHT_KEY] ?: "Default" }
    var fontWeightStyle by remember { mutableStateOf("Default") }

    val fontStyleFlow = context.dataStore.data.map { it[FONT_STYLE_KEY] ?: "Default" }
    var fontStyleType by remember { mutableStateOf("Default") }

    LaunchedEffect(fontWeightFlow) {
        fontWeightStyle = fontWeightFlow.first()
    }

    LaunchedEffect(fontStyleFlow) {
        fontStyleType = fontStyleFlow.first()
    }

    // Determine FontWeight and FontStyle based on preferences
    val fontWeight = when (fontWeightStyle) {
        "Bold" -> FontWeight.Bold
        else -> FontWeight.Normal
    }

    val fontStyle = when (fontStyleType) {
        "Italic" -> FontStyle.Italic
        else -> FontStyle.Normal
    }

    // Sensor listener for all sensors
    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    when (it.sensor.type) {
                        Sensor.TYPE_ACCELEROMETER -> {
                            accelerometerData = "Accelerometer: x=${String.format("%.2f", it.values[0])}, y=${String.format("%.2f", it.values[1])}, z=${String.format("%.2f", it.values[2])}"
                        }
                        Sensor.TYPE_GYROSCOPE -> {
                            gyroscopeData = "Gyroscope: x=${String.format("%.2f", it.values[0])}, y=${String.format("%.2f", it.values[1])}, z=${String.format("%.2f", it.values[2])}"
                        }
                        Sensor.TYPE_MAGNETIC_FIELD -> {
                            magnetometerData = "Magnetometer: x=${String.format("%.2f", it.values[0])}, y=${String.format("%.2f", it.values[1])}, z=${String.format("%.2f", it.values[2])} µT"
                        }
                        Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                            temperatureData = "Temperature: ${String.format("%.2f", it.values[0])} °C"
                        }
                        Sensor.TYPE_LIGHT -> {
                            luxData = "Light (Lux): ${String.format("%.2f", it.values[0])} lx"
                        }
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    // Register all sensors
    DisposableEffect(sensorManager) {
        val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        accSensor?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI) }
        gyroSensor?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI) }
        magSensor?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI) }
        tempSensor?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI) }
        lightSensor?.let { sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI) }

        onDispose { sensorManager.unregisterListener(sensorListener) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Sensor Data Monitor",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display all sensor data
        Text(
            text = accelerometerData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = gyroscopeData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = magnetometerData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = temperatureData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = luxData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        // Font Weight Selection
        Text(text = "Select Font Weight:", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        val fontWeights = listOf("Default", "Bold")
        fontWeights.forEach { weight ->
            Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                RadioButton(
                    selected = (weight == fontWeightStyle),
                    onClick = {
                        fontWeightStyle = weight
                        coroutineScope.launch {
                            context.dataStore.edit { settings ->
                                settings[FONT_WEIGHT_KEY] = weight
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = weight, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Font Style Selection
        Text(text = "Select Font Style:", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        val fontStyles = listOf("Default", "Italic")
        fontStyles.forEach { style ->
            Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                RadioButton(
                    selected = (style == fontStyleType),
                    onClick = {
                        fontStyleType = style
                        coroutineScope.launch {
                            context.dataStore.edit { settings ->
                                settings[FONT_STYLE_KEY] = style
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = style, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Font Size Toggle
        Button(onClick = {
            coroutineScope.launch {
                context.dataStore.edit { settings ->
                    settings[FONT_SIZE_KEY] = if (fontSize == 16) 24 else 16
                }
            }
        }) {
            Text("Toggle Font Size (Current: ${fontSize}sp)")
        }
    }
}
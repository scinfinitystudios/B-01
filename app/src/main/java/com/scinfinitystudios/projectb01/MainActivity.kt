package com.scinfinitystudios.projectb01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Night = Color(0xFF101116)
private val Panel = Color(0xFF1B1D26)
private val Accent = Color(0xFFFF5A36)
private val Muted = Color(0xFFADB1C0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Night
                ) {
                    SensitivityHome()
                }
            }
        }
    }
}

@Composable
private fun SensitivityHome() {
    var device by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("Equilibrado") }
    var general by remember { mutableFloatStateOf(95f) }
    var redDot by remember { mutableFloatStateOf(90f) }
    var scope2x by remember { mutableFloatStateOf(85f) }
    var scope4x by remember { mutableFloatStateOf(80f) }
    var sniper by remember { mutableFloatStateOf(50f) }
    var freeLook by remember { mutableFloatStateOf(75f) }

    fun recommend() {
        when (style) {
            "Preciso" -> {
                general = 88f; redDot = 82f; scope2x = 78f
                scope4x = 70f; sniper = 42f; freeLook = 65f
            }
            "Agresivo" -> {
                general = 100f; redDot = 96f; scope2x = 92f
                scope4x = 86f; sniper = 58f; freeLook = 82f
            }
            else -> {
                general = 95f; redDot = 90f; scope2x = 85f
                scope4x = 80f; sniper = 50f; freeLook = 75f
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Night).padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(18.dp))
            Text("PROJECT B-01", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Sensitivity Pro", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Text("Configura, prueba y ajusta tu sensibilidad.", color = Muted, fontSize = 14.sp)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("TU DISPOSITIVO", color = Color.White, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = device,
                        onValueChange = { device = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Modelo de tu teléfono") },
                        placeholder = { Text("Ej. Redmi Note 13") },
                        singleLine = true
                    )
                    Text("ESTILO DE JUEGO", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Preciso", "Equilibrado", "Agresivo").forEach { option ->
                            FilterChip(
                                selected = style == option,
                                onClick = { style = option },
                                label = { Text(option) }
                            )
                        }
                    }
                    Button(onClick = { recommend() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Generar recomendación")
                    }
                }
            }
        }
        item {
            Text("TU CONFIGURACIÓN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Valores iniciales orientativos. Ajústalos en el juego y prueba qué funciona mejor.", color = Muted, fontSize = 12.sp)
        }
        item { SensitivitySlider("General", general) { general = it } }
        item { SensitivitySlider("Punto rojo", redDot) { redDot = it } }
        item { SensitivitySlider("Mira 2X", scope2x) { scope2x = it } }
        item { SensitivitySlider("Mira 4X", scope4x) { scope4x = it } }
        item { SensitivitySlider("Mira francotirador", sniper) { sniper = it } }
        item { SensitivitySlider("Vista libre", freeLook) { freeLook = it } }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("CONSEJO B-01", color = Accent, fontWeight = FontWeight.Bold)
                    Text(
                        "Prueba los valores en el campo de entrenamiento. Cambia una opción a la vez y conserva la configuración que te resulte cómoda.",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
            Text(
                "Aplicación independiente y no oficial. No modifica Free Fire ni garantiza resultados competitivos.",
                color = Muted,
                fontSize = 11.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
private fun SensitivitySlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(label, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(value.toInt().toString(), color = Accent, fontWeight = FontWeight.Bold)
            }
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 0f..200f
            )
        }
    }
}

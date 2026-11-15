package com.scinfinitystudios.projectb01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

private val deviceCatalog = listOf(
    "Samsung Galaxy A05", "Samsung Galaxy A15", "Samsung Galaxy A25", "Samsung Galaxy A35",
    "Samsung Galaxy A55", "Samsung Galaxy S23", "Samsung Galaxy S24", "Samsung Galaxy S25",
    "Xiaomi Redmi 12", "Xiaomi Redmi 13C", "Xiaomi Redmi Note 13", "Xiaomi Redmi Note 13 Pro",
    "Xiaomi Redmi Note 14", "Xiaomi Redmi Note 14 Pro", "Xiaomi 13T", "Xiaomi 14T",
    "POCO C65", "POCO M6 Pro", "POCO X6 Pro", "POCO F6",
    "Motorola Moto G24", "Motorola Moto G54", "Motorola Moto G84", "Motorola Edge 50",
    "OPPO A38", "OPPO A58", "OPPO Reno 11", "OPPO Reno 12",
    "realme C55", "realme C67", "realme 12", "realme 12 Pro+",
    "vivo Y27", "vivo Y36", "vivo V30",
    "Infinix Hot 40", "Infinix Hot 50", "Infinix Note 40", "Infinix GT 20 Pro",
    "Tecno Spark 20", "Tecno Spark 20 Pro", "Tecno Pova 6", "Tecno Camon 30",
    "Huawei nova 11", "Huawei nova 12", "HONOR X7b", "HONOR X8b", "HONOR 200",
    "Samsung Galaxy A05s", "Samsung Galaxy A14", "Samsung Galaxy A16", "Samsung Galaxy A24",
    "Samsung Galaxy A34", "Samsung Galaxy M14", "Samsung Galaxy M15", "Samsung Galaxy S21",
    "Samsung Galaxy S22", "Samsung Galaxy S24 FE",
    "Xiaomi Redmi 12C", "Xiaomi Redmi 13", "Xiaomi Redmi 14C", "Xiaomi Redmi Note 12",
    "Xiaomi Redmi Note 12 Pro", "Xiaomi Redmi Note 14 Pro+", "Xiaomi 13", "Xiaomi 13 Pro",
    "Xiaomi 14 Pro", "Xiaomi 15",
    "POCO C75", "POCO M6", "POCO X5 Pro", "POCO X6", "POCO X7", "POCO X7 Pro",
    "POCO F5", "POCO F7",
    "Motorola Moto E13", "Motorola Moto E14", "Motorola Moto G14", "Motorola Moto G34",
    "Motorola Moto G55", "Motorola Edge 40", "Motorola Edge 60",
    "OPPO A18", "OPPO A60", "OPPO A78", "OPPO Reno 10", "OPPO Reno 13", "OPPO Find X8",
    "realme C53", "realme C61", "realme C65", "realme 11", "realme 13", "realme 13 Pro",
    "realme GT 6", "realme GT 7",
    "vivo Y17s", "vivo Y28", "vivo Y38", "vivo V29", "vivo V40", "vivo V50",
    "vivo X100", "vivo X200",
    "Infinix Smart 8", "Infinix Smart 9", "Infinix Note 30", "Infinix Note 50",
    "Tecno Spark 30", "Tecno Spark 40", "Tecno Pova 5", "Tecno Pova 7", "Tecno Camon 40",
    "Huawei nova 13", "Huawei Pura 70", "HONOR X6b", "HONOR X9b", "HONOR X9c",
    "HONOR 90", "HONOR Magic 6", "HONOR Magic 7",
    "OnePlus Nord CE 3", "OnePlus Nord CE 4", "OnePlus Nord 4", "OnePlus 12", "OnePlus 13",
    "Google Pixel 7", "Google Pixel 8", "Google Pixel 8a", "Google Pixel 9",
    "ASUS ROG Phone 7", "ASUS ROG Phone 8", "ZTE Nubia Neo 2", "ZTE Nubia Neo 3",
    "ZTE RedMagic 9", "ZTE RedMagic 10"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Night) {
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
    var generated by remember { mutableStateOf(false) }

    val suggestions = remember(device) {
        if (device.isBlank()) emptyList()
        else deviceCatalog.filter { it.contains(device, ignoreCase = true) }.take(6)
    }

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
        generated = true
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Night).padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(Modifier.height(18.dp))
            Text("PROJECT B-01", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Sensitivity Pro", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Text("Una herramienta de ajustes para jugadores Android.", color = Muted, fontSize = 14.sp)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("ENCUENTRA TU DISPOSITIVO", color = Color.White, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = device,
                        onValueChange = { device = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Marca o modelo") },
                        placeholder = { Text("Ej. Redmi Note 13, Galaxy A55...") },
                        singleLine = true
                    )
                    if (suggestions.isNotEmpty()) {
                        Text("MODELOS COINCIDENTES", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        suggestions.forEach { model ->
                            Text(
                                model,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth().clickable { device = model }.padding(vertical = 7.dp)
                            )
                        }
                    }
                    Text("Si tu teléfono no aparece, puedes escribir el modelo manualmente.", color = Muted, fontSize = 12.sp)
                    Text("ESTILO DE JUEGO", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        listOf("Preciso", "Equilibrado", "Agresivo").forEach { option ->
                            FilterChip(selected = style == option, onClick = { style = option }, label = { Text(option) })
                        }
                    }
                    Button(onClick = { recommend() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Generar recomendación")
                    }
                }
            }
        }
        if (generated) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("RECOMENDACIÓN GENERADA", color = Accent, fontWeight = FontWeight.Bold)
                        Text("Perfil $style" + if (device.isNotBlank()) " · $device" else "", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text(
                            "Es un punto de partida por estilo, no una medición exacta del hardware. Prueba y ajusta según tu control.",
                            color = Muted,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
        item {
            Text("TU CONFIGURACIÓN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ajusta cada valor y comprueba cómo se siente en el campo de entrenamiento.", color = Muted, fontSize = 12.sp)
        }
        item { SensitivitySlider("General", general) { general = it } }
        item { SensitivitySlider("Punto rojo", redDot) { redDot = it } }
        item { SensitivitySlider("Mira 2X", scope2x) { scope2x = it } }
        item { SensitivitySlider("Mira 4X", scope4x) { scope4x = it } }
        item { SensitivitySlider("Mira francotirador", sniper) { sniper = it } }
        item { SensitivitySlider("Vista libre", freeLook) { freeLook = it } }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("PLAN GRATUITO", color = Accent, fontWeight = FontWeight.Bold)
                    Text("Recomendaciones, ajustes manuales y perfiles básicos estarán disponibles sin pagar.", color = Color.White, fontSize = 13.sp)
                    Text("PREMIUM · PRÓXIMAMENTE", color = Muted, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Estamos preparando herramientas avanzadas opcionales. No hay compras ni cobros habilitados en esta versión.", color = Muted, fontSize = 12.sp)
                }
            }
            Text(
                "Catálogo inicial ampliable. Si un modelo no aparece, introdúcelo manualmente. Aplicación independiente y no oficial; no modifica Free Fire ni garantiza resultados competitivos.",
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
            Slider(value = value, onValueChange = onValueChange, valueRange = 0f..200f)
        }
    }
}

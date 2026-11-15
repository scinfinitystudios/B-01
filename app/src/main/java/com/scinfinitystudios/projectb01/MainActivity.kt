package com.scinfinitystudios.projectb01

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    private lateinit var billingManager: BillingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billingManager = BillingManager(applicationContext)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Night) {
                    SensitivityHome(billingManager)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::billingManager.isInitialized) billingManager.connect()
    }

    override fun onDestroy() {
        if (::billingManager.isInitialized) billingManager.endConnection()
        super.onDestroy()
    }
}

@Composable
private fun SensitivityHome(billingManager: BillingManager) {
    val isPremium = billingManager.isPremium

    val context = LocalContext.current
    val preferences = remember { context.getSharedPreferences("b01_profiles", Context.MODE_PRIVATE) }
    val phoneName = remember { (Build.MANUFACTURER + " " + Build.MODEL).trim() }
    val memoryInfo = remember {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        ActivityManager.MemoryInfo().also { manager.getMemoryInfo(it) }
    }
    val ramGb = (memoryInfo.totalMem / (1024L * 1024L * 1024L)).toInt()
    val refreshRate = remember {
        @Suppress("DEPRECATION")
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager.defaultDisplay.refreshRate.toInt()
    }
    var device by remember { mutableStateOf(preferences.getString("device", phoneName) ?: phoneName) }
    var style by remember { mutableStateOf(preferences.getString("style", "Equilibrado") ?: "Equilibrado") }
    var general by remember { mutableFloatStateOf(preferences.getFloat("general", 95f)) }
    var redDot by remember { mutableFloatStateOf(preferences.getFloat("redDot", 90f)) }
    var scope2x by remember { mutableFloatStateOf(preferences.getFloat("scope2x", 85f)) }
    var scope4x by remember { mutableFloatStateOf(preferences.getFloat("scope4x", 80f)) }
    var sniper by remember { mutableFloatStateOf(preferences.getFloat("sniper", 50f)) }
    var freeLook by remember { mutableFloatStateOf(preferences.getFloat("freeLook", 75f)) }
    var generated by remember { mutableStateOf(false) }
    var profileName by remember { mutableStateOf("Mi perfil") }
    var selectedProfile by remember { mutableStateOf("") }
    val savedProfiles = remember {
        mutableStateListOf<String>().apply {
            addAll(preferences.getStringSet("profile_names", emptySet()) ?: emptySet())
        }
    }

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
            Text("Detectado: " + phoneName + " · RAM: " + ramGb + " GB · Pantalla: " + refreshRate + " Hz", color = Muted, fontSize = 11.sp)
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
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text(if (isPremium) "PERFILES GUARDADOS · PREMIUM ACTIVO" else "PERFILES GUARDADOS", color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    OutlinedTextField(
                        value = profileName,
                        onValueChange = { profileName = it.take(30) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre del perfil") },
                        placeholder = { Text("Ej. Mi configuración de clasificatoria") },
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            if (!isPremium && savedProfiles.isNotEmpty()) {
                                Toast.makeText(context, "El plan Gratis permite guardar un perfil. Suscríbete a Premium para guardar perfiles ilimitados.", Toast.LENGTH_LONG).show()
                                return@Button
                            }
                            val name = profileName.trim().ifBlank { "Perfil ${savedProfiles.size + 1}" }
                            preferences.edit()
                                .putFloat("profile.$name.general", general)
                                .putFloat("profile.$name.redDot", redDot)
                                .putFloat("profile.$name.scope2x", scope2x)
                                .putFloat("profile.$name.scope4x", scope4x)
                                .putFloat("profile.$name.sniper", sniper)
                                .putFloat("profile.$name.freeLook", freeLook)
                                .putString("profile.$name.style", style)
                                .putString("profile.$name.device", device)
                                .putStringSet("profile_names", (savedProfiles + name).toSet())
                                .apply()
                            if (!savedProfiles.contains(name)) savedProfiles.add(name)
                            selectedProfile = name
                            Toast.makeText(context, "Perfil '$name' guardado", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Guardar nuevo perfil") }
                    if (savedProfiles.isNotEmpty()) {
                        Text("Toca un perfil para cargarlo:", color = Muted, fontSize = 12.sp)
                        savedProfiles.forEach { name ->
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    name + if (selectedProfile == name) " · ACTIVO" else "",
                                    color = if (selectedProfile == name) Accent else Color.White,
                                    modifier = Modifier.weight(1f).clickable {
                                        general = preferences.getFloat("profile.$name.general", 95f)
                                        redDot = preferences.getFloat("profile.$name.redDot", 90f)
                                        scope2x = preferences.getFloat("profile.$name.scope2x", 85f)
                                        scope4x = preferences.getFloat("profile.$name.scope4x", 80f)
                                        sniper = preferences.getFloat("profile.$name.sniper", 50f)
                                        freeLook = preferences.getFloat("profile.$name.freeLook", 75f)
                                        style = preferences.getString("profile.$name.style", "Equilibrado") ?: "Equilibrado"
                                        device = preferences.getString("profile.$name.device", phoneName) ?: phoneName
                                        profileName = name
                                        selectedProfile = name
                                        Toast.makeText(context, "Perfil '$name' cargado", Toast.LENGTH_SHORT).show()
                                    }.padding(vertical = 8.dp)
                                )
                                Text(
                                    "Eliminar",
                                    color = Muted,
                                    modifier = Modifier.clickable {
                                        preferences.edit()
                                            .remove("profile.$name.general")
                                            .remove("profile.$name.redDot")
                                            .remove("profile.$name.scope2x")
                                            .remove("profile.$name.scope4x")
                                            .remove("profile.$name.sniper")
                                            .remove("profile.$name.freeLook")
                                            .remove("profile.$name.style")
                                            .remove("profile.$name.device")
                                            .putStringSet("profile_names", (savedProfiles - name).toSet())
                                            .apply()
                                        savedProfiles.remove(name)
                                        if (selectedProfile == name) selectedProfile = ""
                                    }.padding(8.dp)
                                )
                            }
                        }
                    } else {
                        Text("Todavía no tienes perfiles guardados.", color = Muted, fontSize = 12.sp)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        general = 95f; redDot = 90f; scope2x = 85f
                        scope4x = 80f; sniper = 50f; freeLook = 75f
                        Toast.makeText(context, "Valores restablecidos", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Restablecer valores") }
            }
        }
        item {
            Text("ELIGE TU PLAN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Plan gratuito con publicidad limitada. Premium cuesta US$1 al mes y elimina todos los anuncios.", color = Muted, fontSize = 12.sp)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("GRATUITO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("S/ 0", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                    }
                    Text("Para comenzar a configurar y probar tu sensibilidad.", color = Muted, fontSize = 12.sp)
                    PlanFeature("Catálogo de dispositivos y búsqueda")
                    PlanFeature("Recomendaciones por estilo")
                    PlanFeature("Ajuste manual de todas las sensibilidades")
                    PlanFeature("Guardar tu configuración básica")
                    PlanFeature("Máximo 1 anuncio intersticial por sesión")
                    Text("Los anuncios se mostrarán en momentos naturales, nunca al abrir cada pantalla ni al tocar cada botón.", color = Muted, fontSize = 11.sp)
                    Text("TU PLAN ACTUAL", color = Accent, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF29202A)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("SENSITIVITY PRO", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                            Text("PLAN PREMIUM", color = Accent, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                        Text(billingManager.localizedPrice ?: "US$1 / MES*", color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Text("Para jugadores que quieren llevar un registro y afinar sus ajustes.", color = Color.White, fontSize = 12.sp)
                    PlanFeature("Perfiles personalizados ilimitados")
                    PlanFeature("Calibración guiada paso a paso")
                    PlanFeature("Historial de ajustes y resultados")
                    PlanFeature("Herramientas avanzadas de personalización")
                    PlanFeature("Nuevas funciones Premium a medida que se publiquen")
                    PlanFeature("Sin anuncios")
                    Text("Suscripción mensual con renovación automática. El precio final y la moneda se muestran en la pantalla segura de Google Play. *US$1/mes es el precio objetivo.", color = Muted, fontSize = 12.sp)
                    if (isPremium) {
                        Text("Tu suscripción está activa en este dispositivo.", color = Color(0xFF79D69A), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    } else {
                        Button(
                            onClick = {
                                val activity = context as? Activity
                                if (activity != null) billingManager.launchPurchase(activity)
                                else Toast.makeText(context, "No se pudo abrir Google Play desde esta pantalla.", Toast.LENGTH_SHORT).show()
                            },
                            enabled = billingManager.canPurchase,
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(if (billingManager.canPurchase) "Suscribirme a Premium" else "Suscripción no disponible todavía") }
                    }
                    Button(
                        onClick = { billingManager.refreshPurchases() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Restaurar / comprobar suscripción") }
                    if (billingManager.statusMessage.isNotBlank()) {
                        Text(billingManager.statusMessage, color = Muted, fontSize = 11.sp)
                    }
                    Text("Producto de Google Play: premium_monthly · Base plan mensual con renovación automática. Debes crearlo y activarlo en Play Console antes de vender la suscripción.", color = Muted, fontSize = 11.sp)
                    Text("La verificación segura del token en un servidor y AdMob siguen pendientes antes del lanzamiento público.", color = Muted, fontSize = 11.sp)
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


@Composable
private fun PlanFeature(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("✓", color = Accent, fontWeight = FontWeight.Bold)
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

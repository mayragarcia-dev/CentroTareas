package com.garcia.centrotarea.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.garcia.centrotarea.model.CargaUi
import com.garcia.centrotarea.model.PrimosUi
import com.garcia.centrotarea.viewmodel.TareasViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }
    val colores = MaterialTheme.colorScheme

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("CentroTareas", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colores.primary,
                titleContentColor = colores.onPrimary
            )
        )
    }) { margen ->
        Column(
            modifier = Modifier.fillMaxSize().padding(margen)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaTarea("Tarea con progreso") {
                Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = viewModel::tareaLarga, enabled = !corriendo) {
                        Text("Iniciar")
                    }
                    OutlinedButton(onClick = viewModel::cancelar, enabled = corriendo) {
                        Text("Cancelar")
                    }
                }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${(progreso * 100).roundToInt()} %", style = MaterialTheme.typography.bodySmall)
                Text(mensaje, style = MaterialTheme.typography.bodySmall)
            }
            TarjetaPrimos(viewModel)
            TarjetaCargas(viewModel)
        }
    }
}

@Composable
private fun TarjetaPrimos(viewModel: TareasViewModel) {
    val primos by viewModel.primos.collectAsState()
    val progresoPrimos by viewModel.progresoPrimos.collectAsState()
    var limite by rememberSaveable { mutableStateOf("6000000") }
    var limiteCalculado by rememberSaveable { mutableIntStateOf(0) }
    val hasta = limite.toIntOrNull()?.takeIf { it >= 2 }
    val calculando = primos == PrimosUi.Calculando

    TarjetaTarea("Contar números primos") {
        OutlinedTextField(
            value = limite,
            onValueChange = { limite = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Hasta el número") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            enabled = !calculando
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    hasta?.let {
                        limiteCalculado = it
                        viewModel.contar(it)
                    }
                },
                enabled = !calculando && hasta != null
            ) { Text("Contar") }
            OutlinedButton(onClick = viewModel::cancelarPrimos, enabled = calculando) {
                Text("Cancelar")
            }
        }
        if (calculando || primos == PrimosUi.Cancelado) {
            LinearProgressIndicator(
                progress = { progresoPrimos / 100f },
                modifier = Modifier.fillMaxWidth()
            )
            Text("$progresoPrimos %", style = MaterialTheme.typography.bodySmall)
        }
        when (val estado = primos) {
            PrimosUi.Inicial -> Unit
            PrimosUi.Calculando -> Text("Calculando...")
            PrimosUi.Cancelado -> Text("Cancelado")
            is PrimosUi.Listo -> RecuadroResultado {
                Text("Primos hasta $limiteCalculado: ${estado.cantidad}", fontWeight = FontWeight.Bold)
                Text("Tiempo: ${estado.milisegundos} ms", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun TarjetaCargas(viewModel: TareasViewModel) {
    val carga by viewModel.carga.collectAsState()
    val simularError by viewModel.simularError.collectAsState()
    val secuenciales by viewModel.tiemposSecuenciales.collectAsState()
    val paralelos by viewModel.tiemposParalelos.collectAsState()
    val cargando = carga == CargaUi.Cargando
    val colores = MaterialTheme.colorScheme

    TarjetaTarea("Dos cargas") {
        Row(
            modifier = Modifier.align(Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = simularError,
                onCheckedChange = viewModel::cambiarSimularError,
                modifier = Modifier.semantics { contentDescription = "Simular error" }
            )
            Text("Simular error")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = viewModel::cargarUnaPorUna, enabled = !cargando) {
                Text("Una por una")
            }
            Button(onClick = viewModel::cargarALaVez, enabled = !cargando) {
                Text("A la vez")
            }
        }
        when (val estado = carga) {
            CargaUi.Inicial -> Unit
            CargaUi.Cargando -> CircularProgressIndicator(
                modifier = Modifier.semantics { contentDescription = "Cargando" }
            )
            is CargaUi.Exito -> RecuadroResultado {
                Text("Modo: ${estado.modo}", fontWeight = FontWeight.Bold)
                Text("Perfil: ${estado.perfil}")
                Text("Pedidos: ${estado.pedidos}")
                Text("Tiempo: ${estado.milisegundos} ms", style = MaterialTheme.typography.bodySmall)
            }
            is CargaUi.Error -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = colores.errorContainer,
                    contentColor = colores.onErrorContainer
                ) {
                    Text(
                        text = estado.mensaje,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(onClick = viewModel::cargarALaVez) {
                    Text("Reintentar")
                }
            }
        }
        if (secuenciales.isNotEmpty() || paralelos.isNotEmpty()) {
            TablaTiempos(secuenciales, paralelos)
        }
    }
}

@Composable
private fun TablaTiempos(secuenciales: List<Long>, paralelos: List<Long>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("Comparación de tiempos", style = MaterialTheme.typography.titleSmall)
        FilaTiempos("Una por una", "A la vez", FontWeight.Bold)
        for (indice in 0 until maxOf(secuenciales.size, paralelos.size)) {
            HorizontalDivider()
            FilaTiempos(
                secuenciales.getOrNull(indice)?.let { "$it ms" } ?: "—",
                paralelos.getOrNull(indice)?.let { "$it ms" } ?: "—"
            )
        }
    }
}

@Composable
private fun FilaTiempos(secuencial: String, paralelo: String, peso: FontWeight = FontWeight.Normal) {
    Row(Modifier.fillMaxWidth()) {
        Text(secuencial, Modifier.weight(1f), fontWeight = peso, textAlign = TextAlign.Center)
        Text(paralelo, Modifier.weight(1f), fontWeight = peso, textAlign = TextAlign.Center)
    }
}

@Composable
private fun RecuadroResultado(contenido: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFE0F2E2),
        contentColor = Color(0xFF185B2C)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = contenido
        )
    }
}

@Composable
private fun TarjetaTarea(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    val colores = MaterialTheme.colorScheme
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.outlinedCardColors(containerColor = colores.surface),
        border = BorderStroke(1.dp, colores.outlineVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                modifier = Modifier.align(Alignment.Start),
                color = colores.primary,
                style = MaterialTheme.typography.titleSmall
            )
            contenido()
        }
    }
}

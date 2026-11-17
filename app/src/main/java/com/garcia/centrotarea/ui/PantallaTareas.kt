package com.garcia.centrotarea.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.garcia.centrotarea.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }
    val colores = MaterialTheme.colorScheme
    val forma = MaterialTheme.shapes.medium

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
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = forma,
                color = colores.surface,
                border = BorderStroke(1.dp, colores.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                    Button(onClick = viewModel::tareaLarga) {
                        Text("Tarea larga")
                    }
                    Text(mensaje, style = MaterialTheme.typography.bodySmall)
                }
            }

            Surface(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = forma,
                color = colores.primary.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, colores.primary.copy(alpha = 0.16f))
            ) {
                Text(
                    text = "Los toques siguen sumando mientras trabaja",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = colores.primary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

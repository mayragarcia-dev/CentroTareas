package com.garcia.centrotarea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.garcia.centrotarea.data.TareasRepositoryLocal
import com.garcia.centrotarea.ui.PantallaTareas
import com.garcia.centrotarea.ui.theme.CentroTareasTheme
import com.garcia.centrotarea.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    private val factory = TareasViewModelFactory(TareasRepositoryLocal())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        setContent {
            CentroTareasTheme {
                PantallaTareas(viewModel(factory = factory))
            }
        }
    }
}

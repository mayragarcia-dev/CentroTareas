package com.garcia.centrotarea

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun abreLaActividadPrincipalEnAndroid() {
        ActivityScenario.launch(MainActivity::class.java).use { escenario ->
            assertEquals(Lifecycle.State.RESUMED, escenario.state)
            escenario.onActivity { actividad ->
                assertEquals("com.garcia.centrotarea", actividad.packageName)
            }
        }
    }
}

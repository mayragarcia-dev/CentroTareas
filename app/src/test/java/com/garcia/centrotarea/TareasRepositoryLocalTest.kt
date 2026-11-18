package com.garcia.centrotarea

import com.garcia.centrotarea.data.TareasRepositoryLocal
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TareasRepositoryLocalTest {
    @Test
    fun cuentaLosPrimosHastaElLimiteInclusive() = runTest {
        val repositorio = TareasRepositoryLocal()

        assertEquals(1, repositorio.contarPrimos(2))
        assertEquals(25, repositorio.contarPrimos(100))
        assertEquals(168, repositorio.contarPrimos(1000))
    }

    @Test
    fun notificaAvancesCrecientesHastaElCienPorCiento() = runTest {
        val avances = mutableListOf<Int>()

        TareasRepositoryLocal().contarPrimos(1000) { porcentaje ->
            avances.add(porcentaje)
        }

        assertTrue(avances.all { it in 1..100 })
        assertTrue(avances.zipWithNext().all { (anterior, siguiente) -> anterior < siguiente })
        assertEquals(100, avances.last())
    }
}

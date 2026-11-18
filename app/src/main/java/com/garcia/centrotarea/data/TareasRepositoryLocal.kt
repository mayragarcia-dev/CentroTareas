package com.garcia.centrotarea.data

import android.util.Log
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TareasRepositoryLocal : TareasRepository {
    override fun progresoTarea(): Flow<Float> = flow {
        Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
        for (paso in 1..100) {
            delay(40)
            emit(paso / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    override suspend fun contarPrimos(hasta: Int, alAvanzar: (Int) -> Unit): Int =
        withContext(Dispatchers.Default) {
            val total = hasta.toLong() - 1
            var cantidad = 0
            var ultimoPorcentaje = 0
            for (n in 2..hasta) {
                ensureActive()
                if (esPrimo(n)) cantidad++
                val avance = (n.toLong() - 1) * 100
                if (avance >= total * (ultimoPorcentaje + 1)) {
                    ultimoPorcentaje = (avance / total).toInt()
                    alAvanzar(ultimoPorcentaje)
                }
            }
            cantidad
        }

    private fun esPrimo(n: Int): Boolean {
        var divisor = 2
        while (divisor.toLong() * divisor <= n) {
            if (n % divisor == 0) return false
            divisor++
        }
        return true
    }
}

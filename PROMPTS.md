# Prompts de la Fase 2

Estos prompts están preparados para revisar en Gemini, desde Android Studio, las
tres mejoras de la rama `mejora-ia`. La implementación y las pruebas iniciales se
hicieron con Codex. Las respuestas de Gemini se anotarán después de consultarlo.

## 1. Progreso desde el Repository

> Revisa el commit `3186478` de CentroTareas, una app Android con Kotlin, Compose y
> MVVM + Repository. La tarea emite 100 avances de 0 a 100 % mediante un
> `Flow<Float>` en `TareasRepositoryLocal`, con `delay(40)` y
> `flowOn(Dispatchers.Default)`. El ViewModel recoge el flujo con
> `viewModelScope` y conserva el `Job` para cancelar. Explica qué parte corre en
> cada hilo y revisa qué ocurre si pulso **Cancelar** cerca del final o si intento
> iniciar dos veces. Señala problemas concretos con el archivo y la función
> afectados, propón un cambio mínimo si hace falta y describe cómo verificar
> en el emulador que el porcentaje se conserva al cancelar, vuelve a cero al
> reiniciar y llega a 100 % al completar.

## 2. Primos con progreso

> Revisa el commit `262648a`. `TareasRepositoryLocal` cuenta los primos desde 2
> hasta el límite inclusive en `Dispatchers.Default`, con `ensureActive()` en
> cada vuelta. Notifica al ViewModel solo cuando cambia el porcentaje entero,
> que Compose muestra junto al botón **Cancelar**. Comprueba los casos de límite
> 2, valores grandes, desbordamiento en el porcentaje y cancelación cerca del
> último candidato. Revisa que el ViewModel vuelva a lanzar
> `CancellationException`. Si propones un cambio, conserva el resultado y la
> cancelación. Incluye pruebas para 100, 1000 y 10 000, con resultados esperados
> de 25, 168 y 1229, y una prueba con un límite grande en la que **Tócame** siga
> funcionando durante el conteo.

## 3. Comparación de tiempos

> Revisa el commit `f77ad49`. El ViewModel guarda por separado los tiempos de las
> cargas exitosas **Una por una** y **A la vez**. La tarjeta **Dos cargas**
> muestra las dos columnas y admite un número distinto de mediciones en cada
> modo. Evalúa si el estado y la tabla representan bien una primera carga, dos
> ejecuciones consecutivas del mismo modo, una carga fallida y un reintento
> exitoso. Considera qué sucede al rotar y qué pasa al volver a abrir la app.
> Propón ajustes puntuales si detectas un problema. Incluye una verificación en
> el emulador que compruebe tiempos cercanos a 5000 ms y 3000 ms y confirme que
> solo los éxitos agregan mediciones.

## Registro de revisión

Las dos últimas columnas describen decisiones de la implementación actual. Si
una respuesta de Gemini motiva un cambio, se puede anotar aquí qué se adoptó o
descartó y su motivo.

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| 1. Progreso desde el Repository | Pendiente de consulta. | Moví la simulación al Repository y mantuve la cancelación y el estado de pantalla en el ViewModel. | El Repository produce el avance. El ViewModel conserva el porcentaje visible al cancelar y controla un único trabajo activo. |
| 2. Primos con progreso | Pendiente de consulta. | Publiqué el avance al cambiar el porcentaje entero y conservé `ensureActive()`, con aritmética `Long` y relanzamiento de la cancelación. | Así se limitan las actualizaciones de pantalla, se evita desbordar el cálculo del porcentaje y se puede detener el trabajo de CPU. |
| 3. Comparación de tiempos | Pendiente de consulta. | Guardé tiempos exitosos en memoria por modo. Un error conserva las mediciones y un reintento agrega un tiempo cuando tiene éxito. | La comparación acumula resultados durante el ciclo de vida del ViewModel. Guardarlos entre aperturas corresponde a la mejora independiente de historial. |

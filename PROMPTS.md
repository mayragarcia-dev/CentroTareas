# Prompts de la Fase 2

Estos prompts están preparados para pedirle a Gemini, desde Android Studio, una
propuesta para cada mejora de CentroTareas. Usa la rama `main` como referencia
para comparar las propuestas con lo implementado en `mejora-ia`. La implementación
y las pruebas iniciales se hicieron con Codex. Las respuestas de Gemini se
anotarán después de consultarlo.

## 1. Progreso desde el Repository

> En CentroTareas (`main`), la tarea simulada calcula el avance en
> `TareasViewModel`. Propón cómo mover esa lógica a `TareasRepositoryLocal` y
> exponerla mediante `TareasRepository` como un `Flow<Float>` con
> `flowOn(Dispatchers.Default)`. Conserva los 100 pasos de 40 ms, el `Job` para
> cancelar y el control de doble inicio. Muestra los cambios por archivo y
> explica en qué hilo se emite y se recoge el flujo. Incluye una prueba para
> comprobar que **Cancelar** conserva el porcentaje, que **Iniciar** reinicia
> desde cero y que una ejecución completa llega al 100 %.

## 2. Primos con progreso

> En CentroTareas (`main`), el conteo de primos muestra el resultado y el tiempo
> al finalizar. Propón cómo mostrar el porcentaje de candidatos revisados
> durante el cálculo, con cambios en Repository, ViewModel y Compose. Conserva
> `Dispatchers.Default`, `ensureActive()` y el relanzamiento de
> `CancellationException`. Evita emitir una actualización por cada candidato y
> explica cómo evitar desbordamientos y calcular el avance cuando el límite es 2.
> Incluye una prueba con 1000, cuyo resultado debe ser 168, y otra con un límite
> grande para verificar que el porcentaje avanza, **Tócame** responde y
> **Cancelar** detiene el cálculo.

## 3. Comparación de tiempos

> En CentroTareas (`main`), la tarjeta **Dos cargas** muestra el tiempo de la
> última ejecución. Propón una tabla en Compose que acumule, por separado, los
> tiempos de las cargas exitosas **Una por una** y **A la vez**. El estado debe
> vivir en el ViewModel y la tabla debe admitir cantidades distintas de
> mediciones por modo. Explica cómo se comportará al rotar. Describe qué filas
> deberían quedar después de una carga secuencial, dos paralelas, un error
> simulado y un reintento exitoso. Incluye una prueba con tiempos cercanos a
> 5000 ms y 3000 ms y comprueba que el error conserva los resultados previos.

## Registro de revisión

Las dos últimas columnas describen decisiones de la implementación actual. Si
una respuesta de Gemini motiva un cambio, se puede anotar aquí qué se adoptó o
descartó y su motivo.

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| 1. Progreso desde el Repository | Pendiente de consulta. | Moví la simulación al Repository y mantuve la cancelación y el estado de pantalla en el ViewModel. | El Repository produce el avance. El ViewModel conserva el porcentaje visible al cancelar y controla un único trabajo activo. |
| 2. Primos con progreso | Pendiente de consulta. | Publiqué el avance al cambiar el porcentaje entero y conservé `ensureActive()`, con aritmética `Long` y relanzamiento de la cancelación. | Así se limitan las actualizaciones de pantalla, se evita desbordar el cálculo del porcentaje y se puede detener el trabajo de CPU. |
| 3. Comparación de tiempos | Pendiente de consulta. | Guardé tiempos exitosos en memoria por modo. Un error conserva las mediciones y un reintento agrega un tiempo cuando tiene éxito. | La comparación acumula resultados durante el ciclo de vida del ViewModel. Guardarlos entre aperturas corresponde a la mejora independiente de historial. |

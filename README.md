# CentroTareas

## Observación del Paso 1

Al pulsar **Tarea larga (MAL)**, la pantalla deja de responder durante unos cuatro
segundos y el contador **Tócame** deja de actualizarse durante la espera.
`Thread.sleep(4000)` ocupa el hilo principal, que se encarga tanto de atender
los toques como de dibujar la interfaz.

El ViewModel asigna **Trabajando...**, pero Compose no puede dibujar ese estado
mientras el hilo principal está bloqueado. Cuando se libera, el mensaje vigente
ya es **Terminó**.

Si la espera durara diez segundos y Android detectara eventos de entrada sin
atender a tiempo, podría mostrar el aviso de que la aplicación no responde
(ANR).

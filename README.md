# Project B-01 — Sensitivity Pro

Aplicación Android independiente para crear recomendaciones orientativas de sensibilidad y ayudar a practicar ajustes de control en juegos móviles.

## Estado
Primera base del proyecto. Incluye una pantalla inicial en Jetpack Compose con:
- Selector de estilo: preciso, equilibrado y agresivo.
- Campo para indicar el modelo del teléfono.
- Recomendaciones iniciales editables para General, Punto rojo, Mira 2X, Mira 4X, Francotirador y Vista libre.
- Consejos de ajuste gradual y entrenamiento.

## Tecnología
- Kotlin
- Jetpack Compose
- Android Gradle Plugin
- GitHub Actions para compilar APK y AAB

## Compilar
El workflow **Android build** se ejecuta al subir cambios a `main` o manualmente desde la pestaña Actions.

Los artefactos iniciales son de desarrollo. El APK debug sirve para pruebas. El AAB release generado sin configuración de firma no está listo para subir a Google Play. Antes de publicar configuraremos una clave de carga segura mediante GitHub Actions Secrets y verificaremos todos los requisitos vigentes de Play Console.

## Privacidad y marca
La versión inicial no solicita permisos sensibles, no recopila datos y no modifica Free Fire ni otros juegos. Project B-01 es independiente y no está afiliado, patrocinado ni aprobado por Garena o los titulares de Free Fire. Los nombres de marcas se usan únicamente para describir compatibilidad temática.

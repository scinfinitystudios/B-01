# Project B-01 — Sensitivity Pro

Aplicación Android independiente para ayudar a jugadores a crear, probar y ajustar configuraciones orientativas de sensibilidad.

## Enfoque del producto
- Público general: no está limitada a un único teléfono.
- Catálogo inicial amplio con más de 130 modelos representativos de Samsung, Xiaomi/Redmi, POCO, Motorola, OPPO, realme, vivo, Infinix, Tecno, Huawei, HONOR, OnePlus, Google Pixel, ASUS y ZTE.
- Búsqueda por texto con sugerencias y entrada manual para modelos no incluidos. El catálogo es una selección inicial ampliable, no una lista exhaustiva de todos los teléfonos del mercado.
- Perfiles por estilo: preciso, equilibrado y agresivo.
- Controles editables: General, Punto rojo, Mira 2X, Mira 4X, Francotirador y Vista libre.
- Modelo freemium previsto: plan Gratuito con catálogo, recomendaciones por estilo, ajustes manuales y guardado básico. El plan Gratuito tendrá como máximo un anuncio intersticial por sesión, mostrado en un momento natural y no al abrir cada pantalla ni al tocar cada botón. El plan Premium costará US$1 al mes y no tendrá anuncios. Premium contempla perfiles múltiples, calibración guiada, historial y personalización avanzada. Los perfiles múltiples están disponibles actualmente en modo de prueba; las demás funciones avanzadas siguen en desarrollo. La publicidad y las suscripciones todavía no están activas: requieren integrar y configurar Google AdMob y Google Play Billing, respectivamente.

## Importante sobre las recomendaciones
Los valores actuales son puntos de partida ilustrativos basados en el estilo elegido. El nombre del dispositivo sirve para identificarlo, pero no implica que la app mida automáticamente su hardware ni que exista una sensibilidad universal óptima. El usuario debe probar y ajustar los controles.

## Tecnología
- Kotlin
- Jetpack Compose
- Android Gradle Plugin
- GitHub Actions para compilar APK y AAB

## Compilar
El workflow **Android build** se ejecuta al subir cambios a `main` o manualmente desde la pestaña Actions.

El APK debug sirve para pruebas. El AAB release generado sin configuración de firma no está listo para subir a Google Play. Antes de publicar configuraremos una clave de carga segura mediante GitHub Actions Secrets y verificaremos los requisitos vigentes de Play Console.

## Privacidad, seguridad y marca
La versión inicial no solicita permisos sensibles, no recopila datos y no modifica Free Fire ni otros juegos. Project B-01 es independiente y no está afiliado, patrocinado ni aprobado por Garena o los titulares de Free Fire. Los nombres de marcas y modelos se usan únicamente para describir compatibilidad orientativa.

# Project B-01 — Sensitivity Pro

Aplicación Android independiente para ayudar a jugadores a crear, probar y ajustar configuraciones orientativas de sensibilidad.

## Enfoque del producto
- Público general: no está limitada a un único teléfono.
- Catálogo inicial amplio con más de 130 modelos representativos de Samsung, Xiaomi/Redmi, POCO, Motorola, OPPO, realme, vivo, Infinix, Tecno, Huawei, HONOR, OnePlus, Google Pixel, ASUS y ZTE.
- Búsqueda por texto con sugerencias y entrada manual para modelos no incluidos. El catálogo es una selección inicial ampliable, no una lista exhaustiva de todos los teléfonos del mercado.
- Perfiles por estilo: preciso, equilibrado y agresivo.
- Controles editables: General, Punto rojo, Mira 2X, Mira 4X, Francotirador y Vista libre.
- Modelo freemium previsto: plan Gratuito con catálogo, recomendaciones por estilo, ajustes manuales y guardado básico. El plan Gratuito tendrá como máximo un anuncio intersticial por sesión, mostrado en un momento natural y no al abrir cada pantalla ni al tocar cada botón. El plan Premium es una suscripción mensual con renovación automática, con precio objetivo de US$1 al mes y sin anuncios. El producto de Google Play usa el ID `premium_monthly` y el plan base `monthly`. El plan Gratis permite un perfil guardado; Premium habilita perfiles ilimitados. Calibración guiada, historial y otras herramientas avanzadas siguen en desarrollo. El flujo inicial de Google Play Billing 9.1.0 ya está integrado en el código (consulta de producto y compras, compra, restauración y acknowledge), pero requiere crear y activar el producto en Play Console y probarlo desde una pista de pruebas de Google Play. La verificación segura del token en servidor y la gestión completa del ciclo de vida de suscripciones siguen pendientes antes del lanzamiento público. AdMob todavía no está integrado.

## Importante sobre las recomendaciones
Los valores actuales son puntos de partida ilustrativos basados en el estilo elegido. El nombre del dispositivo sirve para identificarlo, pero no implica que la app mida automáticamente su hardware ni que exista una sensibilidad universal óptima. El usuario debe probar y ajustar los controles.

## Tecnología
- Kotlin
- Jetpack Compose
- Android Gradle Plugin
- GitHub Actions para compilar APK y AAB

## Suscripción Premium

Consulta [PLAY_CONSOLE_SUBSCRIPTION_SETUP.md](PLAY_CONSOLE_SUBSCRIPTION_SETUP.md) para crear el producto `premium_monthly`, configurar el plan mensual `monthly` y probar compras. No publiques la app con el flujo de facturación inicial como única validación: antes se debe integrar la verificación del token de compra en un servidor seguro.

## Compilar
El workflow **Android build** se ejecuta al subir cambios a `main` o manualmente desde la pestaña Actions.

El APK debug sirve para pruebas. El AAB release generado sin configuración de firma no está listo para subir a Google Play. Antes de publicar configuraremos una clave de carga segura mediante GitHub Actions Secrets y verificaremos los requisitos vigentes de Play Console.

## Privacidad, seguridad y marca
La versión inicial no solicita permisos sensibles, no recopila datos y no modifica Free Fire ni otros juegos. Project B-01 es independiente y no está afiliado, patrocinado ni aprobado por Garena o los titulares de Free Fire. Los nombres de marcas y modelos se usan únicamente para describir compatibilidad orientativa.

# Project B-01 — Sensitivity Pro

Aplicación Android independiente dirigida al público general de jugadores móviles.

## Enfoque del producto
- **Catálogo amplio (extensive):** selección inicial de modelos de Samsung, Xiaomi, Redmi, POCO, Motorola, OPPO, realme, vivo, Infinix, Tecno, Huawei, Honor, OnePlus, Google Pixel, ASUS y ZTE.
- **Entrada manual:** si un teléfono no aparece en el catálogo, el usuario puede escribir su marca y modelo.
- **Catálogo ampliable:** la lista inicial es una base y se ampliará en futuras versiones.
- **Modelo freemium:** herramientas básicas gratuitas; funciones Premium opcionales previstas para una versión posterior. Esta versión no procesa pagos ni activa compras.

## Funciones actuales
- Búsqueda de modelos en el catálogo inicial.
- Selector de estilo: preciso, equilibrado y agresivo.
- Valores orientativos editables para General, Punto rojo, Mira 2X, Mira 4X, Francotirador y Vista libre.
- Consejos para probar ajustes de forma gradual.

## Estado y limitaciones
Esta es una primera versión funcional de interfaz. Las recomendaciones todavía son valores orientativos basados en el estilo elegido, no un algoritmo validado por modelo de teléfono ni mediciones de rendimiento. El catálogo contiene modelos representativos, no todos los dispositivos existentes. No se debe anunciar compatibilidad verificada con cada modelo hasta probarla.

Premium está anunciado como función futura; todavía no hay integración de Google Play Billing, compras ni funciones Premium bloqueadas.

## Tecnología y compilación
- Kotlin y Jetpack Compose.
- Android Gradle Plugin.
- GitHub Actions para compilar APK y AAB.

El workflow **Android build** se ejecuta al subir cambios a `main` o manualmente desde la pestaña Actions. El APK debug sirve para pruebas. El AAB release generado sin configuración de firma no está listo para subir a Google Play. Antes de publicar configuraremos una clave de carga segura mediante GitHub Actions Secrets y verificaremos los requisitos vigentes de Play Console.

## Privacidad y marca
La versión inicial no solicita permisos sensibles, no recopila datos y no modifica Free Fire ni otros juegos. Project B-01 es independiente y no está afiliado, patrocinado ni aprobado por Garena o los titulares de Free Fire. Los nombres de marcas y modelos se usan únicamente para identificar dispositivos.

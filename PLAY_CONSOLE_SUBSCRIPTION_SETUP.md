# Configurar la suscripción Premium en Google Play Console

La app ya contiene el flujo inicial de Google Play Billing. Para que el producto aparezca y se pueda probar, configura la misma ficha en Play Console.

## Producto que debe crearse

- Tipo: suscripción.
- ID de producto: `premium_monthly` (debe coincidir exactamente).
- Nombre visible: Project B-01 Premium.
- Plan base: `monthly`.
- Tipo de plan base: renovación automática.
- Periodo de facturación: 1 mes.
- Precio objetivo: equivalente a US$1.00 al mes. Configura el precio por región; Google Play calcula y muestra el precio local al comprador.
- Países/regiones: incluye Perú y los territorios donde quieras vender.

No crees un producto de compra única. Premium debe ser una suscripción mensual renovable.

## Pasos para probar

1. Entra a Google Play Console y selecciona la aplicación cuyo ID de paquete es `com.scinfinitystudios.projectb01`.
2. Abre Monetizar con Play > Productos > Suscripciones y crea `premium_monthly`.
3. Añade el plan base `monthly`, con renovación automática cada mes y el precio que corresponda.
4. Activa el producto y el plan base.
5. Sube una compilación a una pista de pruebas internas.
6. Añade tu cuenta de Google como tester de licencia y acepta la invitación de prueba.
7. Instala la app desde Google Play con esa cuenta. Las compras de prueba se gestionan con el entorno de Play; no uses una APK instalada por fuera de Play para validar el flujo de compra real.
8. Comprueba compra, restauración, cancelación y expiración con las herramientas de prueba de Play Console.

## Estado técnico y requisito antes de publicar

- La app consulta el producto, obtiene la oferta mensual, abre la pantalla de compra de Google Play, consulta las suscripciones existentes, reconoce compras pendientes y compradas y confirma las compras con acknowledge.
- El acceso local Premium solo se activa al recibir una compra en estado PURCHASED de Google Play; una compra pendiente no desbloquea funciones.
- **Falta integrar un servidor seguro que valide el purchase token con Google Play Developer API y mantenga el estado de suscripción.** La comprobación actual en el dispositivo es una integración inicial de desarrollo y no debe considerarse protección antifraude suficiente para producción.
- Antes de publicar, implementar verificación backend, notificaciones en tiempo real (RTDN) y reconciliación del ciclo de vida para contemplar periodos de gracia, suspensión, pausa, reembolso y expiración.
- Google Play Billing no activa AdMob. La publicidad sigue pendiente por separado.

Nunca pongas claves de servicio de Google Play ni credenciales privadas dentro de la app o del repositorio.

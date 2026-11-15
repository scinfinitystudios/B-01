package com.scinfinitystudios.projectb01

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams

/**
 * Google Play subscription client for the premium_monthly subscription.
 *
 * The app only grants access for a PURCHASED subscription returned by Google Play.
 * A secure backend purchase-token verification is still required before public launch.
 */
class BillingManager(context: Context) {
    companion object {
        const val PREMIUM_PRODUCT_ID = "premium_monthly"
    }

    var isPremium by mutableStateOf(false)
        private set

    var localizedPrice by mutableStateOf<String?>(null)
        private set

    var canPurchase by mutableStateOf(false)
        private set

    var statusMessage by mutableStateOf("Conectando con Google Play…")
        private set

    private var isConnecting = false
    private var isReady = false
    private var premiumProduct: ProductDetails? = null

    private val billingClient: BillingClient = BillingClient.newBuilder(context.applicationContext)
        .setListener { billingResult, purchases ->
            handlePurchaseUpdate(billingResult, purchases)
        }
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()
        )
        .enableAutoServiceReconnection()
        .build()

    fun connect() {
        if (isReady || isConnecting) return
        isConnecting = true
        billingClient.startConnection(object : com.android.billingclient.api.BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                isConnecting = false
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isReady = true
                    statusMessage = "Google Play conectado."
                    queryPremiumProduct()
                    refreshPurchases()
                } else {
                    statusMessage = "Google Play no está disponible: ${billingResult.debugMessage}"
                }
            }

            override fun onBillingServiceDisconnected() {
                isReady = false
                statusMessage = "Conexión con Google Play interrumpida. Se reconectará automáticamente."
            }
        })
    }

    private fun queryPremiumProduct() {
        if (!isReady) return
        val product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(PREMIUM_PRODUCT_ID)
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(listOf(product))
            .build()

        billingClient.queryProductDetailsAsync(params) { result, queryResult ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                premiumProduct = queryResult.productDetailsList.firstOrNull()
                val phase = premiumProduct?.subscriptionOfferDetails
                    ?.firstOrNull()
                    ?.pricingPhases
                    ?.pricingPhaseList
                    ?.lastOrNull()
                localizedPrice = phase?.formattedPrice
                canPurchase = premiumProduct?.subscriptionOfferDetails?.isNotEmpty() == true
                if (!canPurchase) {
                    statusMessage = "No se encontró premium_monthly. Crea y activa la suscripción en Play Console."
                } else {
                    statusMessage = "Suscripción mensual disponible en Google Play."
                }
            } else {
                canPurchase = false
                statusMessage = "No se pudo consultar Premium: ${result.debugMessage}"
            }
        }
    }

    fun launchPurchase(activity: Activity) {
        val product = premiumProduct
        val offerToken = product?.subscriptionOfferDetails?.firstOrNull()?.offerToken
        if (!isReady || product == null || offerToken == null) {
            statusMessage = "La suscripción aún no está configurada o disponible en Google Play."
            connect()
            return
        }

        val productParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(product)
            .setOfferToken(offerToken)
            .build()
        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productParams))
            .build()

        val result = billingClient.launchBillingFlow(activity, flowParams)
        if (result.responseCode != BillingClient.BillingResponseCode.OK) {
            statusMessage = "No se pudo iniciar la compra: ${result.debugMessage}"
        }
    }

    fun refreshPurchases() {
        if (!isReady) {
            connect()
            return
        }
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        billingClient.queryPurchasesAsync(params) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val activePremium = purchases.any { purchase ->
                    purchase.products.contains(PREMIUM_PRODUCT_ID) &&
                        purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }
                isPremium = activePremium
                if (activePremium) {
                    statusMessage = "Suscripción Premium activa."
                    purchases.filter { it.products.contains(PREMIUM_PRODUCT_ID) }
                        .forEach { acknowledgeIfNeeded(it) }
                } else {
                    statusMessage = "No se encontró una suscripción Premium activa."
                }
            } else {
                statusMessage = "No se pudo comprobar la suscripción: ${result.debugMessage}"
            }
        }
    }

    private fun handlePurchaseUpdate(result: BillingResult, purchases: List<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases.orEmpty().forEach { processPurchase(it) }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                statusMessage = "Compra cancelada. No se realizó ningún cobro."
            }
            else -> {
                statusMessage = "Google Play informó un problema: ${result.debugMessage}"
            }
        }
    }

    private fun processPurchase(purchase: Purchase) {
        if (!purchase.products.contains(PREMIUM_PRODUCT_ID)) return
        when (purchase.purchaseState) {
            Purchase.PurchaseState.PURCHASED -> acknowledgeIfNeeded(purchase)
            Purchase.PurchaseState.PENDING -> {
                isPremium = false
                statusMessage = "La compra está pendiente. Premium se activará cuando Google Play confirme el pago."
            }
            else -> {
                isPremium = false
                statusMessage = "La compra no está completada."
            }
        }
    }

    private fun acknowledgeIfNeeded(purchase: Purchase) {
        if (purchase.isAcknowledged) {
            isPremium = true
            statusMessage = "Suscripción Premium activa."
            return
        }
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { result ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                isPremium = true
                statusMessage = "Suscripción Premium activa."
            } else {
                isPremium = false
                statusMessage = "Google Play no pudo confirmar la compra: ${result.debugMessage}"
            }
        }
    }

    fun endConnection() {
        if (billingClient.isReady) billingClient.endConnection()
        isReady = false
        isConnecting = false
    }
}

package com.com.mitmeo.mitmeocompanionapp

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable


private const val MITMEO_COMPANION_WEAR_CAPABILITY = "mitmeo_companion_wear"
private const val MITMEO_COMPANION_MESSAGING_PATH = "/mitmeo-companion-messaging"

private const val MESSAGE_BATTERY_INFO = "battery_info"

class WearCommunicationModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    private val messageClient by lazy { Wearable.getMessageClient(this.currentActivity!!) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this.currentActivity!!) }
    //private val nodeClient by lazy { Wearable.getNodeClient(this.currentActivity!!) }

    override fun getName() = "WearCommunicationModule"

    @ReactMethod
    fun requestBatteryInfo() {
        Log.d(name, "requestBatteryInfo()")
        val capabilityInfo: CapabilityInfo = Tasks.await(
            capabilityClient
                .getCapability(
                    MITMEO_COMPANION_WEAR_CAPABILITY,
                    CapabilityClient.FILTER_REACHABLE
                )
        )

        capabilityInfo.nodes.forEach { n ->
            messageClient.sendMessage(
                n.id,
                MITMEO_COMPANION_MESSAGING_PATH,
                MESSAGE_BATTERY_INFO.toByteArray(Charsets.UTF_8)
            ).apply {
                addOnSuccessListener { _ ->
                    Log.d(name, "requestBatteryInfo() success sent ${n.displayName}")
                }
                addOnFailureListener { e ->
                    Log.e(name, "requestBatteryInfo() failed sent ${n.displayName}\nerr: ${e.message}")
                }
            }
        }
    }
}
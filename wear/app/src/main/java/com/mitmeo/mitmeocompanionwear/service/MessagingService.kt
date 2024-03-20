package com.mitmeo.mitmeocompanionwear.service

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mitmeo.mitmeocompanionwear.MITMEO_COMPANION_WEAR_CAPABILITY
import com.mitmeo.mitmeocompanionwear.service.models.BatteryInfo

private const val MESSAGE_BATTERY_INFO = "battery_info"

class MessagingService : WearableListenerService() {

    private val logHeader = MITMEO_COMPANION_WEAR_CAPABILITY
    
    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        Log.d(logHeader, "received $messageEvent")
        when (val msg = messageEvent.data.toString(Charsets.UTF_8)) {
            MESSAGE_BATTERY_INFO -> {
                // TODO: DI
                val batteryInfo = BatteryInfo()
                val payload = mapOf(
                    Pair("__NAME__", msg),
                    Pair("pct", batteryInfo.pct.toString())
                )
                Messenger().send(payload)
            }
        }
    }
}
package com.mitmeo.mitmeocompanionwear.service

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mitmeo.mitmeocompanionwear.MITMEO_COMPANION_WEAR_CAPABILITY
import com.mitmeo.mitmeocompanionwear.MessageKey
import com.mitmeo.mitmeocompanionwear.MessageType
import com.mitmeo.mitmeocompanionwear.presentation.mainActivityInstance
import com.mitmeo.mitmeocompanionwear.service.models.BatteryInfo
import kotlinx.serialization.json.Json

class MessagingService : WearableListenerService() {

    private val logHeader = MITMEO_COMPANION_WEAR_CAPABILITY
    
    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        val receivedPayload = Json.decodeFromString<Map<String,String>>(messageEvent.data.toString(Charsets.UTF_8))
        Log.d(logHeader, "received $receivedPayload")

        val sendPayload = receivedPayload.toMutableMap()

        when (receivedPayload[MessageKey.__TYPE__.name]) {
            MessageType.BATTERY_INFO.name -> {
                // TODO: DI
                sendPayload[MessageKey.BATTERY_PCT.name] = BatteryInfo().pct.toString()
                Messenger(mainActivityInstance).send(sendPayload)
            }
        }
    }
}
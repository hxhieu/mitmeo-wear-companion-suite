package com.mitmeo.mitmeocompanionapp.service

import android.util.Log
import com.facebook.react.bridge.Arguments
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mitmeo.mitmeocompanionapp.MessageKey
import com.mitmeo.mitmeocompanionapp.MessageType
import com.mitmeo.mitmeocompanionapp.rn.wearNativeModule
import kotlinx.serialization.json.Json

class MessagingService : WearableListenerService() {

    private val logHeader = "mitmeo_companion_wear"

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        val receivedPayload =
            Json.decodeFromString<Map<String, String>>(messageEvent.data.toString(Charsets.UTF_8))
        Log.d(logHeader, "received $receivedPayload")

        when (val msgType = receivedPayload[MessageKey.__TYPE__.name]) {
            MessageType.BATTERY_INFO.name -> {
                val pct = receivedPayload[MessageKey.BATTERY_PCT.name]
                val batteryPayload = Arguments.createMap()
                batteryPayload.putString(MessageKey.BATTERY_PCT.name, pct)
                Log.d(logHeader, "sending JS $msgType $batteryPayload")
                wearNativeModule.sendJsEvent(msgType, batteryPayload)
            }
        }
    }
}
package com.mitmeo.mitmeocompanionapp.service

import android.util.Log
import android.widget.Toast
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mitmeo.mitmeocompanionapp.MessageKey
import com.mitmeo.mitmeocompanionapp.MessageType
import kotlinx.serialization.json.Json

class MessagingService : WearableListenerService() {

    private  val logHeader = "mitmeo_companion_wear"
    private fun sendJsEvent(eventName: String, params: WritableMap?) {
        val reactContext = this.applicationContext
        if (reactContext is ReactContext)
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        val receivedPayload = Json.decodeFromString<Map<String,String>>(messageEvent.data.toString(Charsets.UTF_8))
        Log.d(logHeader, "received $receivedPayload")

        when (val msgType = receivedPayload[MessageKey.__TYPE__.name]) {
            MessageType.BATTERY_INFO.name -> {
                val pct = receivedPayload[MessageKey.BATTERY_PCT.name]
                Toast.makeText(
                    this.applicationContext,
                    pct,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
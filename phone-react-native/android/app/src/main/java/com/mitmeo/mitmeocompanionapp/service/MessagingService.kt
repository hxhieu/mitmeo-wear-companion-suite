package com.mitmeo.mitmeocompanionapp.service

import android.util.Log
import android.widget.Toast
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
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
        val msg = messageEvent.data.toString(Charsets.UTF_8)
        Log.d(logHeader, "received '$msg'")
        val payload = Json.decodeFromString<Map<String, String>>(msg)
        val toast = Toast.makeText(this.applicationContext, msg, Toast.LENGTH_LONG)

        //sendJsEvent("")

        when (msg) {
//            MESSAGE_BATTERY_INFO -> {
//                val batteryStatus: Intent? =
//                    IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { i ->
//                        this.applicationContext.registerReceiver(null, i)
//                    }
//                val batteryPct: Float? = batteryStatus?.let { intent ->
//                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
//                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//                    level * 100 / scale.toFloat()
//                }
//                toast.setText(batteryPct.toString())
//            }
        }
        toast.show()
    }
}
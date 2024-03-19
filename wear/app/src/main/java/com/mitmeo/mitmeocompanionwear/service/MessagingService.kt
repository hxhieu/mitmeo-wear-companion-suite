package com.mitmeo.mitmeocompanionwear.service

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mitmeo.mitmeocompanionwear.service.models.BatteryInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val MESSAGE_BATTERY_INFO = "battery_info"

class MessagingService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        Log.d("MessagingService", messageEvent.path)
        val msg = messageEvent.data.toString(Charsets.UTF_8)
        val toast = Toast.makeText(this.applicationContext, msg, Toast.LENGTH_LONG)

        when (msg) {
            MESSAGE_BATTERY_INFO -> {
                val batteryStatus: Intent? =
                    IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { i ->
                        this.applicationContext.registerReceiver(null, i)
                    }
                val batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    level * 100 / scale.toFloat()
                }
                // TODO: DI
                val batteryInfo = BatteryInfo()
                Messenger().send(Json.encodeToString(batteryInfo).toByteArray(Charsets.UTF_8))
                toast.setText(batteryPct.toString())
            }
        }
        toast.show()
    }
}
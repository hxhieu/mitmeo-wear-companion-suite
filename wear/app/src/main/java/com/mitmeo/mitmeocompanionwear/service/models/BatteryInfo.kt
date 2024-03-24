package com.mitmeo.mitmeocompanionwear.service.models

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.mitmeo.mitmeocompanionwear.presentation.mainActivityInstance
import kotlinx.serialization.Serializable

@Serializable
class BatteryInfo {

    var pct: Float? = 0f
        private set;

    constructor() {
        val batteryStatus: Intent? =
            IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { i ->
                mainActivityInstance.applicationContext.registerReceiver(null, i)
            }
        pct = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }
    }
}
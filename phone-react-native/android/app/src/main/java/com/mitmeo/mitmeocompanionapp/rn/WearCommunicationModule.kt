package com.mitmeo.mitmeocompanionapp.rn

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.mitmeo.mitmeocompanionapp.MITMEO_COMPANION_WEAR_CAPABILITY
import com.mitmeo.mitmeocompanionapp.MessageKey
import com.mitmeo.mitmeocompanionapp.MessageType
import com.mitmeo.mitmeocompanionapp.mainActivityInstance
import com.mitmeo.mitmeocompanionapp.service.Messenger

class WearCommunicationModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    // TODO: Shared module
    private  val logHeader = MITMEO_COMPANION_WEAR_CAPABILITY
    
    override fun getName() = "WearCommunicationModule"

    @ReactMethod
    fun requestBatteryInfo() {
        Log.d(logHeader, "requestBatteryInfo()")
        val payload = mapOf(Pair(MessageKey.__TYPE__.name, MessageType.BATTERY_INFO.name))
        Messenger(mainActivityInstance).send(payload)
    }
}
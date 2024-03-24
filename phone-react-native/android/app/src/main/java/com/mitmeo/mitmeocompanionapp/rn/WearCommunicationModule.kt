package com.mitmeo.mitmeocompanionapp.rn

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.mitmeo.mitmeocompanionapp.MITMEO_COMPANION_WEAR_CAPABILITY
import com.mitmeo.mitmeocompanionapp.MessageKey
import com.mitmeo.mitmeocompanionapp.MessageType
import com.mitmeo.mitmeocompanionapp.mainActivityInstance
import com.mitmeo.mitmeocompanionapp.service.Messenger

// TODO: Better way?
lateinit var wearNativeModule: WearCommunicationModule

class WearCommunicationModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun initialize() {
        super.initialize()
        wearNativeModule = this
    }

    // TODO: Shared module
    private val logHeader = MITMEO_COMPANION_WEAR_CAPABILITY

    private var listenerCount = 0

    override fun getName() = "WearCommunicationModule"

    @ReactMethod
    fun requestBatteryInfo() {
        Log.d(logHeader, "requestBatteryInfo()")
        val payload = mapOf(Pair(MessageKey.__TYPE__.name, MessageType.BATTERY_INFO.name))
        Messenger(mainActivityInstance).send(payload)
    }

    fun sendJsEvent(eventName: String, params: WritableMap?) {
        this.reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    @ReactMethod
    fun addListener(eventName: String) {
        if (listenerCount == 0) {
            // TODO: Set up any upstream listeners or background tasks as necessary
        }

        listenerCount += 1
    }

    @ReactMethod
    fun removeListeners(count: Int) {
        listenerCount -= count
        if (listenerCount == 0) {
            // TODO: Remove upstream listeners, stop unnecessary background tasks
        }
    }
}
package com.mitmeo.mitmeocompanionapp.service

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable
import com.mitmeo.mitmeocompanionapp.MITMEO_COMPANION_MESSAGING_PATH
import com.mitmeo.mitmeocompanionapp.MITMEO_COMPANION_WEAR_CAPABILITY
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Messenger(private val activity: Activity) {
    private val messageClient by lazy { Wearable.getMessageClient(activity) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(activity) }

    private val logName = MITMEO_COMPANION_WEAR_CAPABILITY

    fun send(payload: Map<String, String>) {
        Log.d(logName, "sending payload $payload")
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
                Json.encodeToString(payload).toByteArray(Charsets.UTF_8)
            ).apply {
                addOnSuccessListener { _ ->
                    Log.d(logName, "sent successfully ${n.displayName}")
                }
                addOnFailureListener { e ->
                    Log.e(
                        logName,
                        "sent failed ${n.displayName}\nerr: ${e.message}"
                    )
                }
            }
        }
    }
}
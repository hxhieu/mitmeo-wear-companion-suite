package com.mitmeo.mitmeocompanionwear.service

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable
import com.mitmeo.mitmeocompanionwear.MITMEO_COMPANION_MESSAGING_PATH
import com.mitmeo.mitmeocompanionwear.MITMEO_COMPANION_WEAR_CAPABILITY
import com.mitmeo.mitmeocompanionwear.presentation.mainActivityInstance

class Messenger() {
    private val messageClient by lazy { Wearable.getMessageClient(mainActivityInstance) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(mainActivityInstance) }
    private val logName = Messenger::class.simpleName

    fun send(payload: ByteArray){
        Log.d(logName, "Sending payload")
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
                payload
            ).apply {
                addOnSuccessListener { _ ->
                    Log.d(logName, "Sent successfully ${n.displayName}")
                }
                addOnFailureListener { e ->
                    Log.e(
                        logName,
                        "Sent failed ${n.displayName}\nerr: ${e.message}"
                    )
                }
            }
        }
    }
}
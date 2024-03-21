package com.mitmeo.mitmeocompanionapp

// TODO: Move this to shared package

enum class MessageType {
    BATTERY_INFO
}

enum class MessageKey {
    __TYPE__,
    BATTERY_PCT
}

const val MITMEO_COMPANION_WEAR_CAPABILITY = "mitmeo_companion_wear"
const val MITMEO_COMPANION_MESSAGING_PATH = "/mitmeo-companion-messaging"
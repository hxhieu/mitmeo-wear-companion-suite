package com.mitmeo.mitmeocompanionwear.service.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageData<T>(val name: String, val payload: T)
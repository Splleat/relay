package me.splleat.relay.core.application.message.dto

data class SendMessageResult(
    val isCreated: Boolean,
    val message: MessageResult,
)

package me.splleat.relay.core.application.message.dto

data class MessageSendResult(
    val isCreated: Boolean,
    val message: MessageResult,
)

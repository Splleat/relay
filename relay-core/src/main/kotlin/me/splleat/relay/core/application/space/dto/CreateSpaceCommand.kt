package me.splleat.relay.core.application.space.dto

data class CreateSpaceCommand(
    val userId: Long,
    val name: String,
)

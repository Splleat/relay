package me.splleat.relay.core.application.space.dto

data class SpaceCreateCommand(
    val userId: Long,
    val name: String,
)

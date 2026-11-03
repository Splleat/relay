package me.splleat.messengercore.application.user

data class UserRegisterCommand(
    val name: String,
    val email: String,
    val rawPassword: String
)

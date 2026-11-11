package me.splleat.relay.app.rest.space.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import me.splleat.relay.core.application.space.dto.CreateSpaceCommand

data class CreateSpaceRequest(

    @field:NotBlank
    @field:Size(max = 50)
    val name: String,
) {
    fun toCommand(userId: Long) =
        CreateSpaceCommand(userId = userId, name = name)
}

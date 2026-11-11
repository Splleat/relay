package me.splleat.relay.app.rest.channel

import jakarta.validation.Valid
import me.splleat.relay.app.rest.channel.dto.ChannelResponse
import me.splleat.relay.app.rest.channel.dto.OpenDirectChannelRequest
import me.splleat.relay.app.rest.channel.dto.CreateSpaceChannelRequest
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.core.application.channel.GetChannelUseCase
import me.splleat.relay.core.application.channel.OpenDirectChannelUseCase
import me.splleat.relay.core.application.channel.CreateSpaceChannelUseCase
import me.splleat.relay.core.application.channel.dto.GetChannelCommand
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ChannelController(
    private val openDirectChannelUseCase: OpenDirectChannelUseCase,
    private val createSpaceChannelUseCase: CreateSpaceChannelUseCase,
    private val getChannelUseCase: GetChannelUseCase,
) {

    @PostMapping("/dms")
    fun openDirectChannel(
        @RequestBody @Valid request: OpenDirectChannelRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Long> {
        val channelId = openDirectChannelUseCase.execute(request.toCommand(userId))

        return ResponseEntity.ok(channelId)
    }

    @PostMapping("/spaces/{space-id}/channels")
    fun createSpaceChannel(
        @PathVariable("space-id") spaceId: Long,
        @RequestBody @Valid request: CreateSpaceChannelRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Unit> {
        val channelId = createSpaceChannelUseCase.execute(request.toCommand(userId, spaceId))
        val location = createLocation("/api/v1/channels/{id}", channelId)

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/channels/{channel-id}")
    fun getChannel(
        @PathVariable("channel-id") channelId: Long,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<ChannelResponse> {
        val result = getChannelUseCase.execute(GetChannelCommand(userId, channelId))

        return ResponseEntity.ok(ChannelResponse.from(result))
    }
}

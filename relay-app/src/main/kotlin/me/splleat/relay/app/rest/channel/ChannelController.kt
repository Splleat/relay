package me.splleat.relay.app.rest.channel

import jakarta.validation.Valid
import me.splleat.relay.app.rest.channel.dto.ChannelResponse
import me.splleat.relay.app.rest.channel.dto.DirectChannelGetOrCreateRequest
import me.splleat.relay.app.rest.channel.dto.SpaceChannelCreateRequest
import me.splleat.relay.app.rest.createLocation
import me.splleat.relay.core.application.channel.ChannelGetUseCase
import me.splleat.relay.core.application.channel.DirectChannelGetOrCreateUseCase
import me.splleat.relay.core.application.channel.SpaceChannelCreateUseCase
import me.splleat.relay.core.application.channel.dto.ChannelGetCommand
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ChannelController(
    private val directChannelGetOrCreateUseCase: DirectChannelGetOrCreateUseCase,
    private val spaceChannelCreateUseCase: SpaceChannelCreateUseCase,
    private val channelGetUseCase: ChannelGetUseCase,
) {

    @PostMapping("/dms")
    fun getOrCreateDirectChannel(
        @RequestBody @Valid request: DirectChannelGetOrCreateRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Long> {
        val channelId = directChannelGetOrCreateUseCase.execute(request.toCommand(userId))

        return ResponseEntity.ok(channelId)
    }

    @PostMapping("/spaces/{space-id}/channels")
    fun createSpaceChannel(
        @PathVariable("space-id") spaceId: Long,
        @RequestBody @Valid request: SpaceChannelCreateRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Void> {
        val channelId = spaceChannelCreateUseCase.execute(request.toCommand(userId, spaceId))
        val location = createLocation("/api/v1/channels/{id}", channelId)

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/channels/{channel-id}")
    fun getChannel(
        @PathVariable("channel-id") channelId: Long,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<ChannelResponse> {
        val result = channelGetUseCase.execute(ChannelGetCommand(userId, channelId))

        return ResponseEntity.ok(ChannelResponse.from(result))
    }
}

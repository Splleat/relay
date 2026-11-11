package me.splleat.relay.app.rest.message

import jakarta.validation.Valid
import me.splleat.relay.app.rest.message.dto.MessageSendRequest
import me.splleat.relay.app.rest.message.dto.MessageSendResponse
import me.splleat.relay.core.application.message.MessageSendUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val messageSendUseCase: MessageSendUseCase
) {

    @PostMapping("/channels/{channel-id}/messages")
    fun sendMessage(
        @PathVariable("channel-id") channelId: Long,
        @RequestBody @Valid request: MessageSendRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<MessageSendResponse> {
        val result = messageSendUseCase.execute(request.toCommand(userId, channelId))

        if (result.isCreated)
            return ResponseEntity.status(HttpStatus.CREATED).body(MessageSendResponse.from(result))

        return ResponseEntity.ok(MessageSendResponse.from(result))
    }
}

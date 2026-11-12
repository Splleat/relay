package me.splleat.relay.app.rest.message

import jakarta.validation.Valid
import me.splleat.relay.app.rest.message.dto.ChannelMessagesResponse
import me.splleat.relay.app.rest.message.dto.MessageCursorCondition
import me.splleat.relay.app.rest.message.dto.SendMessageRequest
import me.splleat.relay.app.rest.message.dto.SendMessageResponse
import me.splleat.relay.core.application.message.GetChannelMessagesUseCase
import me.splleat.relay.core.application.message.SendMessageUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChannelMessagesUseCase: GetChannelMessagesUseCase
) {

    @PostMapping("/channels/{channel-id}/messages")
    fun sendMessage(
        @PathVariable("channel-id") channelId: Long,
        @RequestBody @Valid request: SendMessageRequest,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<SendMessageResponse> {
        val result = sendMessageUseCase.execute(request.toCommand(userId, channelId))

        if (result.isCreated)
            return ResponseEntity.status(HttpStatus.CREATED).body(SendMessageResponse.from(result))

        return ResponseEntity.ok(SendMessageResponse.from(result))
    }

    @GetMapping("/channels/{channel-id}/messages")
    fun getChannelMessages(
        @PathVariable("channel-id") channelId: Long,
        @ModelAttribute @Valid condition: MessageCursorCondition,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<ChannelMessagesResponse> {
        val result = getChannelMessagesUseCase.execute(condition.toCommand(userId, channelId))

        return ResponseEntity.ok(ChannelMessagesResponse.from(result))
    }
}

package me.splleat.relay.app.rest.message

import me.splleat.relay.app.rest.message.dto.MessageResponse
import me.splleat.relay.core.application.message.dto.MessageCreatedEvent
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MessageBroadcastListener(
    private val messagingTemplate: SimpMessagingTemplate
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun publish(event: MessageCreatedEvent) {
        messagingTemplate.convertAndSend(
            "/sub/channels/${event.message.channelId}",
            MessageResponse.from(event.message)
        )
    }
}

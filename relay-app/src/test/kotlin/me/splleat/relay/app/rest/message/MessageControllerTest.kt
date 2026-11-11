package me.splleat.relay.app.rest.message

import com.ninjasquad.springmockk.MockkBean
import io.mockk.bdd.given
import me.splleat.relay.app.rest.message.dto.SendMessageRequest
import me.splleat.relay.core.application.message.SendMessageUseCase
import me.splleat.relay.core.application.message.dto.MessageResult
import me.splleat.relay.core.application.message.dto.SendMessageResult
import me.splleat.relay.support.WithTestUser
import me.splleat.relay.support.postJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime
import java.util.UUID

@WebMvcTest(MessageController::class)
class MessageControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var sendMessageUseCase: SendMessageUseCase

    @Test
    @WithTestUser
    fun `idempotencyKey가 존재하지 않으면 400을 반환한다`() {
        // given
        val request = mapOf("content" to "test")

        // when
        val result = mockMvc.postJson("/api/v1/channels/1/messages", request)

        // then
        result.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @WithTestUser
    fun `content가 1000자를 넘으면 400을 반환한다`() {
        // given
        val request = SendMessageRequest(
            "a".repeat(1001),
            UUID.randomUUID()
        )

        // when
        val result = mockMvc.postJson("/api/v1/channels/1/messages", request)

        // then
        result.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @WithTestUser
    fun `content가 1000자면 201을 반환한다`() {
        // given
        val request = SendMessageRequest(
            "a".repeat(1000),
            UUID.randomUUID()
        )

        val expected = SendMessageResult(
            true,
            MessageResult(
                1L,
                1L,
                1L,
                request.content,
                LocalDateTime.now()
            )
        )

        given { sendMessageUseCase.execute(request.toCommand(1L, 1L)) } returns expected

        // when
        val result = mockMvc.postJson("/api/v1/channels/1/messages", request)

        // then
        result.andExpect {
            status { isCreated() }
            jsonPath("$.message.senderId") { value(expected.message.senderId)}
        }
    }

    @Test
    @WithTestUser
    fun `isCreated가 true면 201, false면 200을 반환한다`() {
        val userId = 1L
        val channelId = 1L

        val request = SendMessageRequest(
            "test",
            UUID.randomUUID()
        )

        val expected1 = SendMessageResult(
            true,
            MessageResult(
                1L,
                channelId,
                userId,
                request.content,
                LocalDateTime.now()
            )
        )

        val expected2 = expected1.copy(isCreated = false)

        // when
        given { sendMessageUseCase.execute(request.toCommand(userId, channelId)) } returns expected1
        val result1 = mockMvc.postJson("/api/v1/channels/${channelId}/messages", request)

        given { sendMessageUseCase.execute(request.toCommand(userId, channelId)) } returns expected2
        val result2 = mockMvc.postJson("/api/v1/channels/${channelId}/messages", request)

        // then
        result1.andExpect {
            status { isCreated() }
        }

        result2.andExpect {
            status { isOk() }
        }
    }
}

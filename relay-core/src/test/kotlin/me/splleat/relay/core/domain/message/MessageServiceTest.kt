package me.splleat.relay.core.domain.message

import io.kotest.matchers.shouldBe
import io.mockk.bdd.given
import io.mockk.mockk
import me.splleat.relay.core.fixture.createMessage
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Limit

class MessageServiceTest {
    private val repository = mockk<MessageRepository>()
    private val service = MessageService(repository)

    @Test
    fun `커서 이전 메시지 조회 시 커서 이전의 limit개의 메시지를 오름차순으로 반환한다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val cursorId = 1L
        val size = 3
        val limit = 5
        // (테스트 메시지3) <- ... <- (테스트 메시지1) <- (Cursor)
        val messages = (1..size).map { createMessage(userId, channelId, "테스트 메시지$it") }.asReversed()

        given { repository.findAllByChannelIdAndIdLessThanOrderByIdDesc(channelId, cursorId, Limit.of(limit + 1)) } returns messages

        // when
        val result = service.getMessagesBeforeCursor(channelId, cursorId, limit)

        result.hasMore shouldBe false
        result.messages.first().content shouldBe "테스트 메시지1"
        result.messages.last().content shouldBe "테스트 메시지3"
    }

    @Test
    fun `커서 이후 메시지 조회 시 커서 이후 limit개의 메시지를 오름차순으로 반환한다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val cursorId = 1L
        val limit = 5
        // (Cursor) -> (테스트 메시지1) -> ... -> (테스트 메시지6)
        val messages = (1..limit + 1).map { createMessage(userId, channelId, "테스트 메시지$it") }

        given { repository.findAllByChannelIdAndIdGreaterThanOrderByIdAsc(channelId, cursorId, Limit.of(limit + 1)) } returns messages

        // when
        val result = service.getMessagesAfterCursor(channelId, cursorId, limit)

        result.messages.size shouldBe limit

        result.hasMore shouldBe true
        result.messages.first().content shouldBe "테스트 메시지1"
        result.messages.last().content shouldBe "테스트 메시지${limit}"
    }

    @Test
    fun `새로운 메시지 조회 시 최신 메시지부터 이전 limit개의 메시지를 오름차순으로 반환한다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val limit = 5
        // 테스트 메시지6, ... 테스트 메시지1 <- Cursor
        val messages = (1..limit + 1).map { createMessage(userId, channelId, "테스트 메시지$it") }.asReversed()

        given { repository.findAllByChannelIdOrderByIdDesc(channelId, Limit.of(limit + 1)) } returns messages

        // when
        val result = service.getNewestMessages(channelId, limit)

        result.messages.size shouldBe limit

        result.hasMore shouldBe true
        result.messages.first().content shouldBe "테스트 메시지2"
        result.messages.last().content shouldBe "테스트 메시지${limit + 1}"
    }

    @Test
    fun `채널 메시지 조회 시 정확히 limit개의 메시지가 반환되면 hasMore가 false이다`() {
        // given
        val userId = 1L
        val channelId = 1L
        val limit = 5
        // 테스트 메시지5, ... 테스트 메시지1 <- Cursor
        val messages = (1..limit).map { createMessage(userId, channelId, "테스트 메시지$it") }.asReversed()

        given { repository.findAllByChannelIdOrderByIdDesc(channelId, Limit.of(limit + 1)) } returns messages

        // when
        val result = service.getNewestMessages(channelId, limit)

        result.messages.size shouldBe limit

        result.hasMore shouldBe false
        result.messages.first().content shouldBe "테스트 메시지1"
        result.messages.last().content shouldBe "테스트 메시지$limit"
    }
}

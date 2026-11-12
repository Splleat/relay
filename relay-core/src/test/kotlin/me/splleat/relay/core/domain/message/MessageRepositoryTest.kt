package me.splleat.relay.core.domain.message

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import me.splleat.relay.core.domain.channel.ChannelRepository
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.fixture.saveDirectChannel
import me.splleat.relay.core.fixture.saveMessage
import me.splleat.relay.core.fixture.saveUser
import me.splleat.relay.core.support.RepositoryTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Limit

@RepositoryTest
class MessageRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @BeforeEach
    fun setUp() {
        // 메시지 조회가 다른 채널의 메시지를 조회하지 않는지 검증하기 위한 더미 데이터
        val otherUser = userRepository.saveUser()
        val otherChannel = channelRepository.saveDirectChannel()
        repeat(5) { messageRepository.saveMessage(otherUser.requireId(), otherChannel.requireId()) }
    }

    @Test
    fun `커서 이전 메시지 조회 시 커서 메시지를 제외한 이전 N개 메시지를 ID 내림차순으로 조회한다`() {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()
        val size = 25
        val limit = 10
        val saved = (1..size).map { messageRepository.saveMessage(user.requireId(), channel.requireId(), "테스트 메시지$it") }
        // (테스트 메시지1), ..., (테스트 메시지6 - Cursor), ... (테스트 메시지25)
        val cursorIndex = 5
        val cursor = saved[cursorIndex]

        // when
        val result = messageRepository.findAllByChannelIdAndIdLessThanOrderByIdDesc(channel.requireId(), cursor.requireId(), Limit.of(limit))

        // then
        result.size shouldBe 5
        result.first().content shouldBe saved[cursorIndex - 1].content
        result.last().content shouldBe saved.first().content
        result.map { it.id } shouldContainExactly saved.subList(0, cursorIndex).reversed().map { it.id }
    }

    @Test
    fun `커서 이후 메시지 조회 시 커서 메시지를 제외한 이후 N개 메시지를 ID 오름차순으로 조회한다`() {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()
        val size = 25
        val limit = 20
        val saved = (1..size).map { messageRepository.saveMessage(user.requireId(), channel.requireId(), "테스트 메시지$it") }
        // (테스트 메시지1), ..., (테스트 메시지16 - Cursor), ... (테스트 메시지25)
        val cursorIndex = 15
        val cursor = saved[cursorIndex]

        // when
        val result = messageRepository.findAllByChannelIdAndIdGreaterThanOrderByIdAsc(channel.requireId(), cursor.requireId(), Limit.of(limit))

        // then
        result.size shouldBe 9
        result.first().content shouldBe saved[cursorIndex + 1].content
        result.last().content shouldBe saved.last().content
        result.map { it.id } shouldContainExactly saved.subList(cursorIndex + 1, size).map { it.id }
    }

    @Test
    fun `최신 메시지 조회 시 가장 최근 N개 메시지를 ID 내림차순으로 조회한다`() {
        // given
        val user = userRepository.saveUser()
        val channel = channelRepository.saveDirectChannel()
        val size = 25
        val limit = 20
        val saved = (1..size).map { messageRepository.saveMessage(user.requireId(), channel.requireId(), "테스트 메시지$it") }

        // when
        val result = messageRepository.findAllByChannelIdOrderByIdDesc(channel.requireId(), Limit.of(limit))

        // then
        result.size shouldBe limit
        result.first().content shouldBe "테스트 메시지$size"
        result.last().content shouldBe "테스트 메시지${size - limit + 1}"
        result.map { it.id } shouldContainExactly saved.subList(size - limit, size).reversed().map { it.id }
    }
}

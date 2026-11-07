package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.channel.dto.ChannelGetCommand
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.*
import me.splleat.relay.core.domain.space.Space
import me.splleat.relay.core.domain.space.SpaceRepository
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class ChannelGetUseCaseTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var spaceRepository: SpaceRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var channelMemberRepository: ChannelMemberRepository

    @Autowired
    private lateinit var useCase: ChannelGetUseCase

    @Test
    fun `채널에 속하지 않은 사용자가 채널을 조회하면 예외가 발생한다`() {
        // given
        val user = User.register("테스트", "test@test.com", "password")
            .also(userRepository::save)
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)
        val channel = Channel.createSpaceChannel(space.requireId(), "스페이스 채널")
            .also(channelRepository::save)

        val command = ChannelGetCommand(user.requireId(), channel.requireId())

        // when
        val exception = assertThrows<BusinessException> { useCase.execute(command) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(ChannelErrorCode.NOT_FOUND)
    }

    @Test
    fun `채널 이름과 채널에 속한 사용자 목록을 확인할 수 있다`() {
        // given
        val user1 = User.register("테스트1", "test1@test.com", "password")
            .also(userRepository::save)
        val user2 = User.register("테스트2", "test2@test.com", "password")
            .also(userRepository::save)
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)
        val channel = Channel.createSpaceChannel(space.requireId(), "스페이스 채널")
            .also(channelRepository::save)

        channelMemberRepository.save(ChannelMember.create(user1.requireId(), channel.requireId()))
        channelMemberRepository.save(ChannelMember.create(user2.requireId(), channel.requireId()))

        val command = ChannelGetCommand(user2.requireId(), channel.requireId())

        // when
        val result = useCase.execute(command)

        // then
        assertThat(result.id)
            .isEqualTo(channel.id)
        assertThat(result.name)
            .isEqualTo(channel.name)
        assertThat(result.channelMemberList)
            .extracting("userId", "name")
            .containsExactlyInAnyOrder(
                tuple(user1.id, "테스트1"),
                tuple(user2.id, "테스트2"),
            )
    }
}

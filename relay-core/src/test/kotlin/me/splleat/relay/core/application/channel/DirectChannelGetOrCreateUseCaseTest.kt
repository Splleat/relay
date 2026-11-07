package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.channel.dto.DirectChannelGetOrCreateCommand
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.ChannelMemberRepository
import me.splleat.relay.core.domain.channel.ChannelRepository
import me.splleat.relay.core.domain.channel.DirectChannelRepository
import me.splleat.relay.core.domain.space.*
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class DirectChannelGetOrCreateUseCaseTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var spaceRepository: SpaceRepository

    @Autowired
    private lateinit var spaceMemberRepository: SpaceMemberRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var channelMemberRepository: ChannelMemberRepository

    @Autowired
    private lateinit var directChannelRepository: DirectChannelRepository

    @Autowired
    private lateinit var useCase: DirectChannelGetOrCreateUseCase

    @Test
    fun `같은 상대방 사용자와의 DM 채널에 재입장하면 채널이 다시 생성되지 않는다`() {
        // given
        val user1 = User.register("테스트1", "test1@test.com", "password")
            .also(userRepository::save)
        val user2 = User.register("테스트2", "test2@test.com", "password")
            .also(userRepository::save)
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)

        spaceMemberRepository.save(SpaceMember.createOwner(user1.requireId(), space.requireId()))
        spaceMemberRepository.save(SpaceMember.createMember(user2.requireId(), space.requireId()))

        val command = DirectChannelGetOrCreateCommand(user1.requireId(), user2.requireId())

        // when
        val channelId1 = useCase.execute(command)
        val channelId2 = useCase.execute(command)

        // then
        assertThat(channelId1)
            .isEqualTo(channelId2)
        assertThat(channelRepository.count())
            .isOne()
        assertThat(channelMemberRepository.count())
            .isEqualTo(2)
        assertThat(directChannelRepository.count())
            .isOne()
    }

    @Test
    fun `상대방과 같은 공유 스페이스에 속해 있지 않다면 예외가 발생하고 DM 채널이 생성되지 않는다`() {
        // given
        val user1 = User.register("테스트1", "test1@test.com", "password")
            .also(userRepository::save)
        val user2 = User.register("테스트2", "test2@test.com", "password")
            .also(userRepository::save)

        val command = DirectChannelGetOrCreateCommand(user1.requireId(), user2.requireId())

        // when
        val exception = assertThrows<BusinessException> { useCase.execute(command) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceMemberErrorCode.NOT_FOUND)
        assertThat(channelRepository.count())
            .isZero()
    }

    @Test
    fun `자기 자신과의 DM을 열면 채널 사용자가 1명이다`() {
        // given
        val user = User.register("테스트1", "test1@test.com", "password")
            .also(userRepository::save)

        val command = DirectChannelGetOrCreateCommand(user.requireId(), user.requireId())

        // when
        useCase.execute(command)

        // then
        assertThat(channelRepository.count())
            .isOne()
        assertThat(channelMemberRepository.count())
            .isOne()
    }
}

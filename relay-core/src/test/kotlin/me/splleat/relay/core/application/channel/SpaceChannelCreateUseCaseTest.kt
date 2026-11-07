package me.splleat.relay.core.application.channel

import me.splleat.relay.core.application.channel.dto.SpaceChannelCreateCommand
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.ChannelMemberRepository
import me.splleat.relay.core.domain.channel.ChannelRepository
import me.splleat.relay.core.domain.space.*
import me.splleat.relay.core.domain.user.User
import me.splleat.relay.core.domain.user.UserRepository
import me.splleat.relay.core.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class SpaceChannelCreateUseCaseTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var spaceRepository: SpaceRepository

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var channelMemberRepository: ChannelMemberRepository

    @Autowired
    private lateinit var spaceMemberRepository: SpaceMemberRepository

    @Autowired
    private lateinit var useCase: SpaceChannelCreateUseCase

    @Test
    fun `스페이스에 속하지 않은 사용자가 스페이스 채널을 생성하면 예외가 발생하고 채널이 생성되지 않는다`() {
        // given
        val user = User.register("테스트", "test@test.com", "password")
            .also(userRepository::save)
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)

        val command = SpaceChannelCreateCommand(user.requireId(), space.requireId(), "스페이스 채널")

        // when
        val exception = assertThrows<BusinessException> { useCase.execute(command) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceMemberErrorCode.NOT_FOUND)
        assertThat(channelRepository.count())
            .isZero()
    }

    @Test
    fun `스페이스 소유자가 아닌 사용자가 스페이스 채널을 생성하면 예외가 발생하고 채널이 생성되지 않는다`() {
        // given
        val user = User.register("테스트", "test@test.com", "password")
            .also(userRepository::save)
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)

        spaceMemberRepository.save(SpaceMember.createMember(user.requireId(), space.requireId()))

        val command = SpaceChannelCreateCommand(user.requireId(), space.requireId(), "스페이스 채널")

        // when
        val exception = assertThrows<BusinessException> { useCase.execute(command) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceMemberErrorCode.FORBIDDEN)
        assertThat(channelRepository.count())
            .isZero()
    }

    @Test
    fun `스페이스 채널이 생성되면 모든 스페이스 참여자가 해당 채널의 멤버가 된다`() {
        val space = Space.createShared("스페이스")
            .also(spaceRepository::save)

        val owner = User.register("테스트1", "test1@test.com", "password")
            .also(userRepository::save)
        val member = User.register("테스트2", "test2@test.com", "password")
            .also(userRepository::save)

        spaceMemberRepository.save(SpaceMember.createOwner(owner.requireId(), space.requireId()))
        spaceMemberRepository.save(SpaceMember.createMember(member.requireId(), space.requireId()))

        val command = SpaceChannelCreateCommand(owner.requireId(), space.requireId(), "스페이스 채널")

        // when
        useCase.execute(command)

        // then
        assertThat(channelRepository.count())
            .isOne()
        assertThat(channelMemberRepository.count())
            .isEqualTo(2)
    }
}

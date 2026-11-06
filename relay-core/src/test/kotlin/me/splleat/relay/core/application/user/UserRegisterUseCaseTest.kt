package me.splleat.relay.core.application.user

import me.splleat.relay.core.application.user.dto.UserRegisterCommand
import me.splleat.relay.core.domain.space.SpaceMemberRepository
import me.splleat.relay.core.domain.space.SpaceRepository
import me.splleat.relay.core.domain.space.SpaceRole
import me.splleat.relay.core.domain.space.SpaceType
import me.splleat.relay.core.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

@IntegrationTest
class UserRegisterUseCaseTest {

    @Autowired
    private lateinit var userRegisterUseCase: UserRegisterUseCase

    @Autowired
    private lateinit var spaceRepository: SpaceRepository

    @Autowired
    private lateinit var spaceMemberRepository: SpaceMemberRepository

    @Test
    fun `사용자가 가입하면 개인 스페이스가 생성된다`() {
        // given
        val command = UserRegisterCommand("테스트", "test@test.com", "password")

        // when
        val userId = userRegisterUseCase.execute(command)

        // then
        val spaceMember = spaceMemberRepository.findByUserId(userId).single()

        assertThat(spaceMember.spaceRole)
            .isEqualTo(SpaceRole.OWNER)

        val space = spaceRepository.findByIdOrNull(spaceMember.spaceId)

        assertThat(space?.type)
            .isEqualTo(SpaceType.PERSONAL)
    }
}

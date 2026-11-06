package me.splleat.relay.core.domain.space

import io.mockk.bdd.given
import io.mockk.mockk
import me.splleat.relay.core.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SpaceMemberServiceTest {
    private val repository = mockk<SpaceMemberRepository>()
    private val service = SpaceMemberService(repository)

    @Test
    fun `스페이스 사용자 검증이 실패하면 예외가 발생한다`() {
        // given
        val userId = 1L
        val spaceId = 1L

        given { repository.existsByUserIdAndSpaceId(userId, spaceId) } returns false

        // when
        val exception = assertThrows<BusinessException> { service.validateSpaceMember(userId, spaceId) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceErrorCode.NOT_FOUND)
    }
}

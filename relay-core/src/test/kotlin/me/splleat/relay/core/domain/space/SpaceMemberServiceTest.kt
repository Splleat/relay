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
    fun `존재하지 않는 스페이스 사용자를 조회하면 예외가 발생한다`() {
        // given
        val userId = 1L
        val spaceId = 1L

        given { repository.findByUserIdAndSpaceId(userId, spaceId) } returns null

        // when
        val exception = assertThrows<BusinessException> { service.getSpaceMember(userId, spaceId) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceMemberErrorCode.NOT_FOUND)
    }

    @Test
    fun `상대방과 같은 스페이스에 속해 있지 않으면 예외가 발생한다`() {
        // given
        val userId = 1L
        val targetId = 2L

        given { repository.existsSharedSpace(userId, targetId) } returns false

        // when
        val exception = assertThrows<BusinessException> { service.validShareSpace(userId, targetId) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceMemberErrorCode.NOT_FOUND)
    }
}

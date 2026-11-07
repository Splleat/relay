package me.splleat.relay.core.domain.space

import io.mockk.bdd.given
import io.mockk.bdd.then
import io.mockk.mockk
import me.splleat.relay.core.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

class SpaceServiceTest {
    private val repository = mockk<SpaceRepository>()
    private val service = SpaceService(repository)

    @Test
    fun `존재하지 않는 스페이스를 조회하면 예외가 발생한다`() {
        // given
        val id = 1L

        given { repository.findByIdOrNull(id) } returns null

        // when
        val exception = assertThrows<BusinessException> { service.getSpace(id) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(SpaceErrorCode.NOT_FOUND)
    }

    @Test
    fun `빈 리스트로 스페이스 목록을 조회하면 DB 접근 없이 빈 리스트를 반환한다`() {
        // given
        val ids = emptyList<Long>()

        // when
        val result = service.getSpaces(ids)

        // then
        assertThat(result)
            .isEmpty()

        then(exactly = 0) { repository.findAllById(any()) }
    }
}

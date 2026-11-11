package me.splleat.relay.app.rest.space

import com.ninjasquad.springmockk.MockkBean
import io.mockk.bdd.given
import io.mockk.bdd.then
import me.splleat.relay.app.common.SecurityConfig
import me.splleat.relay.app.rest.space.dto.CreateSpaceRequest
import me.splleat.relay.core.application.space.CreateSpaceUseCase
import me.splleat.relay.core.application.space.GetSpaceUseCase
import me.splleat.relay.core.application.space.GetJoinedSpacesUseCase
import me.splleat.relay.core.application.space.dto.CreateSpaceCommand
import me.splleat.relay.core.application.space.dto.JoinedSpacesResult
import me.splleat.relay.core.application.space.dto.SpaceResult
import me.splleat.relay.core.application.space.dto.SpaceSummaryResult
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.space.SpaceErrorCode
import me.splleat.relay.core.domain.space.SpaceType
import me.splleat.relay.support.WithTestUser
import me.splleat.relay.support.postJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(SpaceController::class)
@Import(SecurityConfig::class)
class SpaceControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var createSpaceUseCase: CreateSpaceUseCase

    @MockkBean
    private lateinit var getJoinedSpacesUseCase: GetJoinedSpacesUseCase

    @MockkBean
    private lateinit var getSpaceUseCase: GetSpaceUseCase

    @Test
    @WithTestUser(id = 1L)
    fun `스페이스를 생성하면 201과 Location 헤더를 반환한다`() {
        // given
        val request = CreateSpaceRequest(name = "테스트 스페이스")
        val expectedSpaceId = 10L

        given { createSpaceUseCase.execute(CreateSpaceCommand(userId = 1L, name = "테스트 스페이스")) } returns expectedSpaceId

        // when
        val result = mockMvc.postJson("/api/v1/spaces", request)

        // then
        result.andExpect {
            status { isCreated() }
            header { string("Location", "http://localhost/api/v1/spaces/$expectedSpaceId") }
        }
    }

    @Test
    @WithTestUser
    fun `스페이스 이름이 공백이면 400을 반환한다`() {
        // given
        val request = CreateSpaceRequest(name = "")

        // when
        val result = mockMvc.postJson("/api/v1/spaces", request)

        // then
        result.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_INPUT") }
        }

        then(exactly = 0) { createSpaceUseCase.execute(any()) }
    }

    @Test
    @WithTestUser
    fun `스페이스 이름이 50자를 넘으면 400을 반환한다`() {
        // given
        val request = CreateSpaceRequest("a".repeat(51))

        // when
        val result = mockMvc.postJson("/api/v1/spaces", request)

        // then
        result.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_INPUT") }
        }

        then(exactly = 0) { createSpaceUseCase.execute(any()) }
    }

    @Test
    fun `인증되지 않은 사용자가 스페이스 생성을 요청하면 401을 반환한다`() {
        // given
        val request = CreateSpaceRequest(name = "테스트 스페이스")

        // when
        val result = mockMvc.postJson("/api/v1/spaces", request)

        // then
        result.andExpect {
            status { isUnauthorized() }
            jsonPath("$.code") { value("AUTH_UNAUTHENTICATED") }
        }

        then(exactly = 0) { createSpaceUseCase.execute(any()) }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `내 스페이스 목록을 조회하면 200과 목록을 반환한다`() {
        // given
        val spaceList = listOf(
            SpaceSummaryResult(id = 1L, name = "개인 스페이스", type = SpaceType.PERSONAL),
            SpaceSummaryResult(id = 2L, name = "팀 스페이스", type = SpaceType.SHARED),
        )

        given { getJoinedSpacesUseCase.execute(1L) } returns JoinedSpacesResult(spaceList)

        // when
        val result = mockMvc.get("/api/v1/spaces")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.spaces[0].id") { value(1L) }
            jsonPath("$.spaces[0].name") { value("개인 스페이스") }
            jsonPath("$.spaces[0].type") { value("PERSONAL") }
            jsonPath("$.spaces[1].id") { value(2L) }
            jsonPath("$.spaces[1].name") { value("팀 스페이스") }
            jsonPath("$.spaces[1].type") { value("SHARED") }
        }
    }

    @Test
    fun `인증되지 않은 사용자가 스페이스 목록을 조회하면 401을 반환한다`() {
        // when
        val result = mockMvc.get("/api/v1/spaces")

        // then
        result.andExpect {
            status { isUnauthorized() }
            jsonPath("$.code") { value("AUTH_UNAUTHENTICATED") }
        }

        then(exactly = 0) { getJoinedSpacesUseCase.execute(any()) }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `스페이스 단건을 조회하면 200과 스페이스 정보를 반환한다`() {
        // given
        val spaceId = 10L
        val spaceResult = SpaceResult(id = spaceId, name = "팀 스페이스", type = SpaceType.SHARED)

        given { getSpaceUseCase.execute(1L, spaceId) } returns spaceResult

        // when
        val result = mockMvc.get("/api/v1/spaces/$spaceId")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(spaceId) }
            jsonPath("$.name") { value("팀 스페이스") }
            jsonPath("$.type") { value("SHARED") }
        }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `존재하지 않는 스페이스를 조회하면 404를 반환한다`() {
        // given
        val spaceId = 999L

        given { getSpaceUseCase.execute(1L, spaceId) } throws BusinessException(SpaceErrorCode.NOT_FOUND)

        // when
        val result = mockMvc.get("/api/v1/spaces/$spaceId")

        // then
        result.andExpect {
            status { isNotFound() }
            jsonPath("$.code") { value("SPACE_NOT_FOUND") }
        }
    }
}

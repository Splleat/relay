package me.splleat.relay.app.rest.channel

import com.ninjasquad.springmockk.MockkBean
import io.mockk.bdd.given
import io.mockk.bdd.then
import me.splleat.relay.app.common.SecurityConfig
import me.splleat.relay.app.rest.channel.dto.OpenDirectChannelRequest
import me.splleat.relay.app.rest.channel.dto.CreateSpaceChannelRequest
import me.splleat.relay.core.application.channel.GetChannelUseCase
import me.splleat.relay.core.application.channel.OpenDirectChannelUseCase
import me.splleat.relay.core.application.channel.CreateSpaceChannelUseCase
import me.splleat.relay.core.application.channel.dto.ChannelMemberResult
import me.splleat.relay.core.application.channel.dto.ChannelResult
import me.splleat.relay.core.application.channel.dto.OpenDirectChannelCommand
import me.splleat.relay.core.application.channel.dto.CreateSpaceChannelCommand
import me.splleat.relay.core.application.channel.dto.GetChannelCommand
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.channel.ChannelErrorCode
import me.splleat.relay.core.domain.channel.ChannelType
import me.splleat.relay.core.domain.space.SpaceMemberErrorCode
import me.splleat.relay.support.WithTestUser
import me.splleat.relay.support.postJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ChannelController::class)
@Import(SecurityConfig::class)
class ChannelControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var openDirectChannelUseCase: OpenDirectChannelUseCase

    @MockkBean
    private lateinit var createSpaceChannelUseCase: CreateSpaceChannelUseCase

    @MockkBean
    private lateinit var getChannelUseCase: GetChannelUseCase

    @Test
    @WithTestUser(id = 1L)
    fun `DM 채널을 가져오거나 생성하면 200과 channelId를 반환한다`() {
        // given
        val request = OpenDirectChannelRequest(targetId = 2L)
        val expectedChannelId = 100L

        given { openDirectChannelUseCase.execute(OpenDirectChannelCommand(userId = 1L, targetId = 2L)) } returns expectedChannelId

        // when
        val result = mockMvc.postJson("/api/v1/dms", request)

        // then
        result.andExpect {
            status { isOk() }
            content { string(expectedChannelId.toString()) }
        }
    }

    @Test
    fun `인증되지 않은 사용자가 DM 생성을 요청하면 401을 반환한다`() {
        // given
        val request = OpenDirectChannelRequest(targetId = 2L)

        // when
        val result = mockMvc.postJson("/api/v1/dms", request)

        // then
        result.andExpect {
            status { isUnauthorized() }
            jsonPath("$.code") { value("AUTH_UNAUTHENTICATED") }
        }

        then(exactly = 0) { openDirectChannelUseCase.execute(any()) }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `스페이스 채널을 생성하면 201과 채널 Location 헤더를 반환한다`() {
        // given
        val spaceId = 1L
        val request = CreateSpaceChannelRequest(channelName = "일반")
        val expectedChannelId = 10L

        given { createSpaceChannelUseCase.execute(CreateSpaceChannelCommand(userId = 1L, spaceId = spaceId, channelName = "일반")) } returns expectedChannelId

        // when
        val result = mockMvc.postJson("/api/v1/spaces/$spaceId/channels", request)

        // then
        result.andExpect {
            status { isCreated() }
            header { string("Location", "http://localhost/api/v1/channels/$expectedChannelId") }
        }
    }

    @Test
    @WithTestUser
    fun `스페이스 채널 이름이 공백이면 400을 반환한다`() {
        // given
        val request = CreateSpaceChannelRequest(channelName = "")

        // when
        val result = mockMvc.postJson("/api/v1/spaces/1/channels", request)

        // then
        result.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_INPUT") }
        }

        then(exactly = 0) { createSpaceChannelUseCase.execute(any()) }
    }

    @Test
    fun `인증되지 않은 사용자가 스페이스 채널 생성을 요청하면 401을 반환한다`() {
        // given
        val request = CreateSpaceChannelRequest(channelName = "일반")

        // when
        val result = mockMvc.postJson("/api/v1/spaces/1/channels", request)

        // then
        result.andExpect {
            status { isUnauthorized() }
            jsonPath("$.code") { value("AUTH_UNAUTHENTICATED") }
        }

        then(exactly = 0) { createSpaceChannelUseCase.execute(any()) }
    }

    @Test
    @WithTestUser(id = 2L)
    fun `스페이스 관리자가 아닌 사용자가 채널을 생성하려 하면 403을 반환한다`() {
        // given
        val spaceId = 1L
        val request = CreateSpaceChannelRequest(channelName = "일반")

        given { createSpaceChannelUseCase.execute(any()) } throws BusinessException(SpaceMemberErrorCode.FORBIDDEN)

        // when
        val result = mockMvc.postJson("/api/v1/spaces/$spaceId/channels", request)

        // then
        result.andExpect {
            status { isForbidden() }
            jsonPath("$.code") { value("SPACE_MEMBER_FORBIDDEN") }
        }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `채널을 단건 조회하면 200과 채널 상세 정보를 반환한다`() {
        // given
        val channelId = 10L
        val channelResult = ChannelResult(
            id = channelId,
            channelType = ChannelType.SPACE,
            name = "일반",
            channelMemberList = listOf(
                ChannelMemberResult(userId = 1L, name = "테스트유저", imageUrl = null)
            )
        )

        given { getChannelUseCase.execute(GetChannelCommand(userId = 1L, channelId = channelId)) } returns channelResult

        // when
        val result = mockMvc.get("/api/v1/channels/$channelId")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(channelId) }
            jsonPath("$.channelType") { value("SPACE") }
            jsonPath("$.name") { value("일반") }
            jsonPath("$.channelMemberList[0].userId") { value(1L) }
            jsonPath("$.channelMemberList[0].name") { value("테스트유저") }
        }
    }

    @Test
    fun `인증되지 않은 사용자가 채널 조회를 요청하면 401을 반환한다`() {
        // when
        val result = mockMvc.get("/api/v1/channels/10")

        // then
        result.andExpect {
            status { isUnauthorized() }
            jsonPath("$.code") { value("AUTH_UNAUTHENTICATED") }
        }

        then(exactly = 0) { getChannelUseCase.execute(any()) }
    }

    @Test
    @WithTestUser(id = 1L)
    fun `존재하지 않는 채널을 조회하면 404를 반환한다`() {
        // given
        val channelId = 999L

        given { getChannelUseCase.execute(any()) } throws BusinessException(ChannelErrorCode.NOT_FOUND)

        // when
        val result = mockMvc.get("/api/v1/channels/$channelId")

        // then
        result.andExpect {
            status { isNotFound() }
            jsonPath("$.code") { value("CHANNEL_NOT_FOUND") }
        }
    }
}

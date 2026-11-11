package me.splleat.relay.app.rest.user

import com.ninjasquad.springmockk.MockkBean
import io.mockk.bdd.given
import io.mockk.bdd.then
import me.splleat.relay.app.common.SecurityConfig
import me.splleat.relay.app.rest.user.dto.SignUpRequest
import me.splleat.relay.core.application.user.GetMyProfileUseCase
import me.splleat.relay.core.application.user.SignUpUseCase
import me.splleat.relay.core.application.user.dto.UserResult
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.user.UserErrorCode
import me.splleat.relay.support.WithTestUser
import me.splleat.relay.support.postJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UserController::class)
@Import(SecurityConfig::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var signUpUseCase: SignUpUseCase

    @MockkBean
    private lateinit var getMyProfileUseCase: GetMyProfileUseCase

    @Test
    fun `사용자를 등록하면 201을 반환한다`() {
        // given
        val request = SignUpRequest("테스트", "test@test.com", "password")
        val expectedId = 1L

        given { signUpUseCase.execute(request.toCommand()) } returns expectedId

        // when
        val result = mockMvc.postJson("/api/v1/users", request)

        // then
        result.andExpect {
            status { isCreated() }
            header { string("Location", "http://localhost/api/v1/users/${expectedId}") }
        }
    }

    @Test
    fun `이미 사용 중인 이메일로 사용자를 등록하면 409를 반환한다`() {
        // given
        val request = SignUpRequest("테스트", "test@test.com", "password")

        given { signUpUseCase.execute(any()) } throws BusinessException(UserErrorCode.EMAIL_DUPLICATED)

        // when
        val result = mockMvc.postJson("/api/v1/users", request)

        // then
        result.andExpect {
            status { isConflict() }
            jsonPath("$.code") { value("USER_EMAIL_DUPLICATED") }
        }
    }

    @Test
    fun `잘못된 이메일 형식으로 사용자를 등록하면 400을 반환한다`() {
        // given
        val request = SignUpRequest("테스트", "invalid-email", "password")

        // when
        val result = mockMvc.postJson("/api/v1/users", request)

        // then
        result.andExpect {
            status { isBadRequest() }
        }

        then(exactly = 0) { signUpUseCase.execute(any()) }
    }

    @Test
    fun `잘못된 JSON 형식으로 사용자를 등록하면 400을 반환한다`() {
        // given
        val invalidJson = """
            {"name": "테스트", 
        """.trimIndent()

        // when
        val result = mockMvc.postJson("/api/v1/users", invalidJson)

        // then
        result.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("BAD_REQUEST") }
            jsonPath("$.errors") { isArray() }
        }
    }

    @Test
    @WithTestUser
    fun `내 정보를 조회하면 200을 반환한다`() {
        // given
        val userResult = UserResult("테스트", "test@test.com", "이미지", "상태 메시지")

        given { getMyProfileUseCase.execute(any()) } returns userResult

        // when
        val result = mockMvc.get("/api/v1/users/me") {
            with(csrf())
        }

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("테스트") }
            jsonPath("$.email") { value("test@test.com") }
        }
    }
}

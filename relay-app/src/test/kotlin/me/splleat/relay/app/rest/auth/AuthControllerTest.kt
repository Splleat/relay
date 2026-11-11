package me.splleat.relay.app.rest.auth

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.bdd.given
import io.mockk.bdd.then
import io.mockk.just
import me.splleat.relay.app.common.SecurityConfig
import me.splleat.relay.app.rest.auth.dto.LoginRequest
import me.splleat.relay.app.security.SessionService
import me.splleat.relay.core.application.user.LoginUseCase
import me.splleat.relay.core.common.BusinessException
import me.splleat.relay.core.domain.user.UserErrorCode
import me.splleat.relay.support.postJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(AuthController::class)
@Import(SecurityConfig::class)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var sessionService: SessionService

    @MockkBean
    private lateinit var loginUseCase: LoginUseCase

    @Test
    fun `로그인을 성공하면 204를 반환한다`() {
        // given
        val request = LoginRequest("test@test.com", "password")
        val expectedId = 1L

        given { loginUseCase.execute(request.toCommand()) } returns expectedId
        given { sessionService.saveSession(expectedId, any(), any()) } just Runs

        // when
        val result = mockMvc.postJson("/api/v1/auth/login", request)

        // then
        result.andExpect {
            status { isNoContent() }
        }
        then(exactly = 1) { sessionService.saveSession(expectedId, any(), any()) }
    }

    @Test
    fun `로그인이 실패하면 401을 반환한다`() {
        // given
        val request = LoginRequest("test@test.com", "password")

        given { loginUseCase.execute(request.toCommand()) } throws BusinessException(UserErrorCode.INVALID_CREDENTIALS)

        // when
        val result = mockMvc.postJson("/api/v1/auth/login", request)

        // then
        result.andExpect {
            status { isUnauthorized() }
        }
    }
}

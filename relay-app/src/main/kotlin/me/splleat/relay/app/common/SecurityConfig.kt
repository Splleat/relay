package me.splleat.relay.app.common

import me.splleat.relay.app.security.ApiAccessDeniedHandler
import me.splleat.relay.app.security.ApiAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import tools.jackson.databind.json.JsonMapper

@Configuration(proxyBeanMethods = false)
class SecurityConfig(
    private val jsonMapper: JsonMapper
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/api/v1/auth/login", permitAll)
                authorize("/api/v1/users", permitAll)
                authorize(anyRequest, authenticated)
            }
            logout {
                logoutUrl = "/api/v1/auth/logout"
                logoutSuccessHandler = HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)
            }
            exceptionHandling {
                authenticationEntryPoint = ApiAuthenticationEntryPoint(jsonMapper)
                accessDeniedHandler = ApiAccessDeniedHandler(jsonMapper)
            }
        }

        return http.build()
    }
}
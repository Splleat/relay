package me.splleat.relay.support

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithTestUserSecurityContextFactory : WithSecurityContextFactory<WithTestUser> {

    override fun createSecurityContext(annotation: WithTestUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken(annotation.id, null,emptyList())

        context.authentication = authentication
        return context
    }
}

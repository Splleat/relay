package me.splleat.relay.core.support

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@Import(MysqlTestContainer::class)
annotation class IntegrationTest
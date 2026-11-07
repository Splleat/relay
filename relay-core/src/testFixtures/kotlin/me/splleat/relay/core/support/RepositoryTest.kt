package me.splleat.relay.core.support

import me.splleat.relay.core.common.JpaConfig
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@DataJpaTest
@Import(MysqlTestContainer::class, JpaConfig::class)
annotation class RepositoryTest()

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    `java-test-fixtures`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

dependencies {
    testFixturesApi("org.springframework.boot:spring-boot-testcontainers")
    testFixturesApi("org.testcontainers:testcontainers-mysql")
    testFixturesApi("org.testcontainers:testcontainers-junit-jupiter")
    testFixturesApi("org.springframework.boot:spring-boot-starter-test")
    testFixturesApi("org.jetbrains.kotlin:kotlin-test-junit5")
    testFixturesApi("io.mockk:mockk:1.14.11")
    testFixturesApi("io.mockk:mockk-bdd:1.14.11")
    testFixturesApi("com.ninja-squad:springmockk:5.0.1")
    testFixturesApi("io.kotest:kotest-assertions-core-jvm:6.2.6")
    testFixturesImplementation("org.springframework:spring-jdbc")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.hypersistence:hypersistence-utils-hibernate-73:3.16.0")
    implementation("org.springframework.security:spring-security-crypto")

    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-mysql")
    testFixturesApi("org.springframework.boot:spring-boot-starter-data-jpa-test")
}

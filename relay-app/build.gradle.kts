plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootRun {
    workingDir = rootProject.projectDir
}

dependencies {
    implementation(project(":relay-core"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("tools.jackson.module:jackson-module-kotlin")

    testImplementation(testFixtures(project(":relay-core")))
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

}
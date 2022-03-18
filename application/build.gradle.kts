import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports { mavenBom(SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
    api(projects.domain)
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("io.mockk:mockk:1.12.3")
}

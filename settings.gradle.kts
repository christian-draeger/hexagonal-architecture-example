rootProject.name = "hexagonal-architecture-example"

pluginManagement {
    repositories {
        gradlePluginPortal()
        // maven { url = uri("https://repo.spring.io/release") }
    }
    plugins {
        id("org.springframework.boot") version "2.6.4"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.spring") version "1.6.10"
        // id("org.springframework.experimental.aot") version "0.11.3"
    }
}

include(
    "application",
    "domain",
)

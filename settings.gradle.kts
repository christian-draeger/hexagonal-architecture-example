@file:Suppress("LocalVariableName")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
        id("com.adarshr.test-logger") version "3.1.0"
        id("org.jetbrains.kotlinx.kover") version "0.5.0-RC2"
    }
}

include(
    "domain",
    "infrastructure",
    "application",
)

@file:Suppress("LocalVariableName")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "hexagonal-architecture-example"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("org.springframework.boot") version "2.6.4"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.spring") version "1.6.10"
        id("com.bmuschko.docker-spring-boot-application") version "7.1.0"
        id("com.adarshr.test-logger") version "3.1.0"
        id("org.jetbrains.kotlinx.kover") version "0.5.0-RC2"
        id("org.unbroken-dome.test-sets") version "4.0.0"
    }
}

include(
    "domain",
    "infrastructure",
    "application",
)

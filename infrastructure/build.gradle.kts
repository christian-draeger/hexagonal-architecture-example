plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.bmuschko.docker-spring-boot-application")
}

dependencies {
    api(projects.application)
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.github.rybalkinsd:kohttp:0.12.0")
    val springDocVersion = "1.5.12"
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    val testcontainersVersion = "1.16.3"
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:mongodb:$testcontainersVersion")
}

tasks {
    bootRun {
        args("--spring.profiles.active=testdata")
    }

    dockerCreateDockerfile {
        dependsOn(bootJar)
    }
}

docker {
    springBootApplication {
        baseImage.set("azul/zulu-openjdk-alpine:17-jre")
        maintainer.set(rootProject.name)
        images.set(setOf("${rootProject.name}:latest"))
        ports.set(setOf(8080))
        jvmArgs.set(listOf("-Xmx2048m"))
    }
}

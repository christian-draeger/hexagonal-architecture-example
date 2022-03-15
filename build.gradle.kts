import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    // id("org.springframework.experimental.aot") apply false
}

group = "codes.draeger"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    repositories {
        mavenCentral()
    }

}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
        testImplementation("io.strikt:strikt-core:0.31.0")
    }
    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

val kotlinVersion: String by project
plugins {
    id("java")
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}

group = "org.homework"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    testImplementation(kotlin("test", kotlinVersion))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
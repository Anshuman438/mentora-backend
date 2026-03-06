import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")           version "3.2.3"
    id("io.spring.dependency-management")    version "1.1.4"
    id("org.jetbrains.kotlin.jvm")           version "1.9.22"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.jpa")    version "1.9.22"
}

group   = "com.mentora"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.google.firebase:firebase-admin:9.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    mainClass.set("com.mentora.backend.MentoraBackendApplicationKt")
}

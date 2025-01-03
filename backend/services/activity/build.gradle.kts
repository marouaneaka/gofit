// activity/build.gradle.kts

plugins {
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "gofit.services"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starter for building REST APIs
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')
    // SQLite Database Driver
    implementation("org.xerial:sqlite-jdbc:3.42.0")

    // Kafka Client
    implementation("org.apache.kafka:kafka-clients:3.5.0")

}

tasks.war {
    enabled = true
}
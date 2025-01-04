plugins {
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("war")
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // SQLite Database Driver
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    providedCompile("jakarta.servlet:jakarta.servlet-api:5.0.0")
    providedCompile("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    // Kafka Client
    implementation("org.apache.kafka:kafka-clients:3.5.0")
    // Spring Boot Starter Tomcat
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks.withType<War> {
    enabled = true
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootWar> {
    mainClass.set("gofit.services.activity.ActivityServiceApplication")
}
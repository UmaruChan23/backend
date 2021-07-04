import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.springframework.boot") version "2.5.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20-RC"
	id("com.github.johnrengelman.shadow") version "7.0.0"
	kotlin("jvm") version "1.5.20"
	kotlin("plugin.spring") version "1.5.20"
}

group = "com.example"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

application {
	mainClass.set("com.example.backend.IoTBackendApplicationKt")
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.mbed.java-coap:coap-core:5.0.0")
	runtimeOnly("org.postgresql:postgresql:42.2.22")
	implementation("org.apache.logging.log4j:log4j-core:2.14.1")
	implementation("org.apache.logging.log4j:log4j-api:2.14.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

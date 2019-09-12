import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.1.8.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  kotlin("jvm") version "1.2.71"
  kotlin("plugin.spring") version "1.2.71"
}

group = "com.karumi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.springframework.boot:spring-boot-starter-web")

  testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val test: Test by tasks
test.useJUnitPlatform()

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

object Versions {
  const val ktlint = "0.34.0"
}

object Libraries {
  const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
}

val ktlint by configurations.creating

dependencies {
  ktlint(Libraries.ktlint)
}

tasks.register<JavaExec>("ktlint") {
  group = "verification"
  description = "Check Kotlin code style."
  classpath = ktlint
  main = "com.pinterest.ktlint.Main"
  args("--android", "src/**/*.kt")
}

tasks.named("check") {
  dependsOn(ktlint)
}

tasks.register<JavaExec>("ktlintFormat") {
  group = "formatting"
  description = "Fix Kotlin code style deviations."
  classpath = ktlint
  main = "com.pinterest.ktlint.Main"
  args("--android", "-F", "src/**/*.kt")
}
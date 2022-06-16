plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.20"

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.knowm.xchart:xchart:3.8.1")
}

java {
    withSourcesJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.pokearch.deteck)
    alias(libs.plugins.pokearch.ktlint)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":domain"))
    testImplementation(project(":testing"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.arrow)
    implementation(libs.inject)
}

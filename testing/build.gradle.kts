@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(project(":domain"))
    api(libs.kotlinx.coroutines.core)
    implementation(libs.paging.common)
    api(libs.junit)
    api(libs.turbine)
    api(libs.kluent)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
}

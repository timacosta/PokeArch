plugins {
    alias(libs.plugins.pokearch.library)
    alias(libs.plugins.pokearch.deteck)
    alias(libs.plugins.pokearch.ktlint)
}

dependencies {
    api(project(":domain"))
    implementation(libs.paging.common)
    api(libs.junit)
    api(libs.turbine)
    api(libs.kluent)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
}

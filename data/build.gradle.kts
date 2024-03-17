plugins {
    alias(libs.plugins.pokearch.library)
    alias(libs.plugins.pokearch.deteck)
    alias(libs.plugins.pokearch.ktlint)
}

dependencies {
    implementation(project(":domain"))
    testImplementation(project(":testing"))
    implementation(libs.arrow)
    implementation(libs.inject)
}

plugins {
    alias(libs.plugins.pokearch.library)
    alias(libs.plugins.pokearch.deteck)
    alias(libs.plugins.pokearch.ktlint)
}

dependencies {
    implementation(libs.arrow)
    testImplementation(project(":testing"))
}

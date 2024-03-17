plugins {
    alias(libs.plugins.pokearch.library)
    alias(libs.plugins.pokearch.detekt)
    alias(libs.plugins.pokearch.ktlint)
}
dependencies {
    implementation(project(":domain"))
    testImplementation(project(":testing"))
    implementation(libs.arrow)
    implementation(libs.inject)
}

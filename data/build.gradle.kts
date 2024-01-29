@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.architects.pokearch.data"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.arrow)
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.androix.compiler)
    ksp(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
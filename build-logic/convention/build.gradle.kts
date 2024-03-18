import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.architects.pokearch.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("pokearchLibrary") {
            id = "pokearch.library"
            implementationClass = "PokeArchLibraryConventionPlugin"
        }
        register("pokearchDetekt") {
            id = "pokearch.detekt"
            implementationClass = "PokeArchDetektConventionPlugin"
        }
        register("pokearchKtlint") {
            id = "pokearch.ktlint"
            implementationClass = "PokeArchKtlintConventionPlugin"
        }
    }
}

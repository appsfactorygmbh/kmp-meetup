import com.rickclephas.kmp.nativecoroutines.gradle.ExposedSeverity

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.ksp)
    alias(libs.plugins.skie)
}


kotlin {
    jvmToolchain(17)

    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite)
            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentnegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization)
            api(libs.kmp.observableviewmodel)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
    }

    nativeCoroutines {
        exposedSeverity = ExposedSeverity.ERROR
    }
}

android {
    namespace = "de.appsfactory.kmpmeetup"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

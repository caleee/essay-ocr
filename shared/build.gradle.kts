plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework { baseName = "shared" }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kuikly.core)
                implementation(libs.kuikly.compose)
                implementation(libs.kuikly.core.annotations)
                implementation(compose.runtime)
            }
        }
    }
}

android {
    namespace = "com.tencent.kuikly.essayocr"
    compileSdk = 34
    defaultConfig { minSdk = 24 }
}

dependencies {
    ksp(libs.kuikly.core.ksp)
}

ksp {
    arg("moduleId", "essayocr")
    arg("isMainModule", "true")
}
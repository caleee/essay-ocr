import com.tencent.kuikly.gradle.config.KuiklyConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kuikly.gradle.plugin)
    kotlin("native.cocoapods")
}

val KEY_PAGE_NAME = "pageName"

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        browser {
            webpackTask {
                mainOutputFileName = "essayocr.js"
            }
            commonWebpackConfig {
                output?.library = null
                devtool = "source-map"
            }
        }
        binaries.executable()
    }

    cocoapods {
        summary = "Essay OCR Shared Module"
        homepage = "https://github.com/essay-ocr"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/assets/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kuikly.core)
                implementation(libs.kuikly.compose)
                implementation(libs.kuikly.core.annotations)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.kuikly.core.render.android)
            }
        }
    }
}

android {
    namespace = "com.tencent.kuikly.essayocr"
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    sourceSets {
        named("main") {
            assets.srcDirs("src/commonMain/assets")
        }
    }
}

dependencies {
    compileOnly(libs.kuikly.core.ksp) {
        add("kspAndroid", this)
        add("kspIosArm64", this)
        add("kspIosX64", this)
        add("kspIosSimulatorArm64", this)
        add("kspJs", this)
    }
}

ksp {
    arg(KEY_PAGE_NAME, "")
}

configure<KuiklyConfig> {
    js {
        outputName("essayocr")
    }
}
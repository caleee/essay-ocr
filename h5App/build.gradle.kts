plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js(IR) {
        browser {
            webpackTask {
                mainOutputFileName = "essayocr.js"
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("com.tencent.kuikly-open.core-render-web:base:${libs.versions.kuikly.get()}")
                implementation("com.tencent.kuikly-open.core-render-web:h5:${libs.versions.kuikly.get()}")
            }
        }
    }
}
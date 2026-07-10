pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    }
}

rootProject.name = "essay-ocr"
include(":shared")
include(":androidApp")
include(":h5App")
include(":miniApp")
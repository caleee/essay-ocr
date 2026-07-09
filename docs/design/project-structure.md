# essay-ocr 项目目录结构设计

## 1. 背景

本项目使用 Kuikly Compose DSL 开发跨端 OCR 应用。基于官方技能文档和 KMP 规范设计目录结构。

### 参考来源

- Kuikly 官方技能（`kuikly-ui-framework`, `kuikly-multi-module-config`, `kuikly-assets-resource` 等 12 个）
- Kuikly 官方 rules（`kuiklyComposeDSL.mdc`, `kuiklyDSL.mdc`）
- KMP（Kotlin Multiplatform）标准工程规范

### 技术选型

| 维度 | 选择 |
|------|------|
| UI 框架 | Kuikly Compose DSL |
| 多平台 | KMP（Android / iOS / 后续可扩展鸿蒙、H5） |
| 构建 | Gradle + KSP（KuiklyCoreEntry 代码生成） |
| 语言 | Kotlin |

---

## 2. 整体目录结构

```
essay-ocr/
├── settings.gradle.kts              # 项目模块注册
├── build.gradle.kts                 # 根构建配置
├── gradle.properties                # Gradle/Kotlin 属性
├── gradle/
│   └── libs.versions.toml           # 版本目录（统一管理依赖版本）
│
├── shared/                          # Kuikly 主模块（含 @Page 页面）
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/
│       │   ├── kotlin/
│       │   │   └── com/tencent/kuikly/essayocr/
│       │   │       ├── App.kt                   # KuiklyCoreEntry 入口
│       │   │       ├── di/                      # 依赖注入 / 服务定位
│       │   │       │   └── ServiceLocator.kt
│       │   │       ├── model/                   # 数据模型
│       │   │       │   ├── Essay.kt
│       │   │       │   ├── OCRResult.kt
│       │   │       │   └── RecognitionBlock.kt
│       │   │       ├── pages/                   # @Page 页面
│       │   │       │   ├── HomePage.kt          # 首页（拍照/选图）
│       │   │       │   ├── PreviewPage.kt       # OCR 预览页
│       │   │       │   ├── EditorPage.kt        # 结果编辑页
│       │   │       │   └── HistoryPage.kt       # 历史记录页
│       │   │       ├── components/              # 可复用 Compose 组件
│       │   │       │   ├── CameraView.kt
│       │   │       │   ├── ImageCropper.kt
│       │   │       │   ├── OCRResultCard.kt
│       │   │       │   └── EmptyState.kt
│       │   │       ├── service/                 # 业务服务
│       │   │       │   ├── OCRService.kt        # OCR 识别服务（接口）
│       │   │       │   └── StorageService.kt    # 本地存储服务
│       │   │       └── util/                    # 工具类
│       │   │           ├── ImageUtils.kt
│       │   │           └── TextUtils.kt
│       │   └── assets/                          # 资源文件
│       │           ├── common/                  # 公共资源
│       │           │   └── placeholder.png
│       │           ├── home/                    # HomePage 资源
│       │           ├── preview/                 # PreviewPage 资源
│       │           └── editor/                  # EditorPage 资源
│       ├── androidMain/
│       │   ├── AndroidManifest.xml
│       │   └── kotlin/
│       │       └── com/tencent/kuikly/essayocr/
│       │           ├── service/
│       │           │   └── OCRServiceAndroid.kt # Android OCR 实现
│       │           └── Platform.kt              # expect/actual
│       ├── iosMain/
│       │   └── kotlin/
│       │       └── com/tencent/kuikly/essayocr/
│       │           ├── service/
│       │           │   └── OCRServiceIOS.kt     # iOS OCR 实现
│       │           └── Platform.kt              # expect/actual
│       └── jsMain/                              # 可选（H5）
│           └── kotlin/
│
├── androidApp/                      # Android 壳工程
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/
│       │   └── com/tencent/kuikly/essayocr/
│       │       └── MainActivity.kt
│       └── res/
│           ├── values/
│           │   ├── strings.xml
│           │   └── themes.xml
│           └── mipmap-xxxhdpi/
│               └── ic_launcher.webp
│
├── iosApp/                          # iOS 壳工程（Xcode）
│   ├── iosApp/
│   │   ├── AppDelegate.swift
│   │   ├── SceneDelegate.swift
│   │   ├── ContentView.swift
│   │   └── Info.plist
│   ├── iosApp.xcodeproj/
│   └── Podfile                      # CocoaPods 依赖
│
├── docs/
│   ├── design/                      # 设计文档
│   │   └── project-structure.md     # 本文档
│   └── meta/                        # 项目元信息
│       └── project-requirements.md
│
└── .gitignore
```

---

## 3. 各目录职责说明

### 3.1 shared/ — Kuikly 主模块

核心代码所在，遵循 KMP 标准 source set 布局：

```
shared/src/
├── commonMain/     # 跨平台共享代码
├── androidMain/    # Android 平台实现
└── iosMain/        # iOS 平台实现
```

**commonMain 分包原则**：

| 包 | 职责 | 规则 |
|----|------|------|
| `pages/` | @Page 页面 | 每个文件一个页面，继承 ComposeContainer |
| `components/` | 可复用 UI 组件 | @Composable 函数，无 @Page |
| `model/` | 数据模型 | data class，纯数据结构 |
| `service/` | 业务服务 | 接口定义在 commonMain，实现在各平台 |
| `di/` | 依赖管理 | 服务注册与获取 |
| `util/` | 工具函数 | 纯函数，无状态 |

### 3.2 androidApp/ — Android 壳工程

仅包含 `MainActivity` 和 Android 资源，业务代码全在 `shared` 中。

### 3.3 iosApp/ — iOS 壳工程

Swift UI 壳，通过 CocoaPods 接入 `shared.framework`。

---

## 4. 关键文件配置

### 4.1 settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "essay-ocr"
include(":shared")
include(":androidApp")
```

### 4.2 shared/build.gradle.kts 核心配置

```kotlin
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework { baseName = "shared" }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.tencent.kuikly:core:2.0.21")
                implementation("com.tencent.kuikly:compose:2.0.21")
                implementation("com.tencent.kuikly:core-annotations:2.0.21")
                implementation(compose.runtime)
            }
        }
    }
}

android {
    namespace = "com.tencent.kuikly.essayocr"
    compileSdk = 34
}

dependencies {
    add("ksp", "com.tencent.kuikly:core-ksp:2.0.21")
}

ksp {
    arg("moduleId", "essayocr")
    arg("isMainModule", "true")
}
```

### 4.3 页面模板

```kotlin
@Page("home")
class HomePage : ComposeContainer() {

    override fun willInit() {
        super.willInit()
        setContent { HomeScreen() }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Essay OCR")
    }
}
```

---

## 5. OCR 架构策略

OCR 识别是计算密集型任务，策略如下：

```
┌──────────────┐     ┌──────────────────┐     ┌────────────────┐
│  ImageSource │────>│  OCRService      │────>│  ResultScreen  │
│ (相机/相册)   │     │ (commonMain 接口) │     │ (编辑/导出)     │
└──────────────┘     │                  │     └────────────────┘
                     │  ├ Android:      │
                     │  │  ML Kit / ONNX│
                     │  └ iOS:          │
                     │     Vision/VNRecognizeTextRequest
                     └──────────────────┘
```

OCR 服务接口定义在 `commonMain`，各平台各自实现最优方案（Android 用 ML Kit / ONNX，iOS 用 Vision）。

---

## 6. 扩展预留

### 6.1 多模块支持

当页面增多后，可按功能拆分子模块：

```kotlin
// settings.gradle.kts
include(":feature:ocr")
include(":feature:history")
include(":feature:settings")
```

### 6.2 平台扩展

| 平台 | 目录 | 状态 |
|------|------|------|
| Android | `shared/src/androidMain/` | 就绪 |
| iOS | `shared/src/iosMain/` | 就绪 |
| 鸿蒙 | `shared/src/ohosMain/` | 需要时添加 |
| H5 | `shared/src/jsMain/` | 需要时添加 |

---

## 7. 实施步骤

1. 创建 Gradle wrapper 和构建配置
2. 搭建 `shared/` 模块骨架
3. 搭建 `androidApp/` 壳工程
4. 搭建 `iosApp/` 壳工程
5. 验证编译通过
6. 更新 `.claude/rules/kuiklyComposeDSL.md` 添加 `paths` 作用域

---

## 8. 附录：依赖版本管理

使用 Gradle Version Catalog（`gradle/libs.versions.toml`）统一管理：

```toml
[versions]
kotlin = "2.0.21"
agp = "8.5.0"
ksp = "2.0.21-1.0.27"
kuikly = "2.0.21"
compose-multiplatform = "1.6.0"

[libraries]
kuikly-core = { module = "com.tencent.kuikly:core", version.ref = "kuikly" }
kuikly-compose = { module = "com.tencent.kuikly:compose", version.ref = "kuikly" }
kuikly-core-annotations = { module = "com.tencent.kuikly:core-annotations", version.ref = "kuikly" }
kuikly-core-ksp = { module = "com.tencent.kuikly:core-ksp", version.ref = "kuikly" }

[plugins]
kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
android-library = { id = "com.android.library", version.ref = "agp" }
compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
```
# essay-ocr 项目需求文档

## 项目概述

嵌入式 OCR 方案 App，使用 Kuikly Compose DSL 跨端开发，支持 Android 和 iOS 平台。

## 核心功能

### 1. OCR 文字识别
- 拍照识别纸质文档
- 相册选取图片识别
- 识别结果编辑校对
- 导出纯文本 / Markdown

### 2. 文档管理
- 历史记录列表
- 搜索/筛选
- 删除/清空

## 技术约束

| 维度 | 要求 |
|------|------|
| UI 框架 | Kuikly Compose DSL |
| 多平台 | Android + iOS |
| 最低支持 | Android 5.0+ (API 21), iOS 12.0+ |
| 构建工具 | Gradle 8.7 + AGP 8.5 + KSP |
| 语言 | Kotlin 2.0.21 |

## OCR 方案

| 平台 | 引擎 | 说明 |
|------|------|------|
| Android | ML Kit / ONNX | ML Kit 依赖 Google Play Services（国内需备选） |
| iOS | Vision.framework | `VNRecognizeTextRequest` 系统自带 |

## 发布目标（待确认）

- 国内安卓应用商店
- Google Play
- App Store
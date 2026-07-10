# essay-ocr 项目整体计划

> 项目周期划分为 7 个阶段，从立项到发布。当前进度：**Phase 0 进行中**。

---

## Phase 0: 项目初始化 [进行中]

### 已完成
- 确定技术栈（Kuikly Compose DSL + KMP）
- 安装 Kuikly 官方 skills（12 个）
- 安装 Kuikly 官方 rules（kuiklyComposeDSL）
- 设计项目目录结构（`docs/design/project-structure.md`）
- 搭建项目骨架并首次提交
- 创建 Gradle wrapper（Gradle 8.7→8.13）
- 配置版本组合（Kotlin 2.1.21 + AGP 8.13.2 + KSP）
- 配置 Kuikly 依赖与 Tencent Maven 仓库
- 添加 KSP 配置（`core-ksp`、`pageName`）
- 搭建 androidApp 宿主（KuiklyRenderActivity + 7 适配器 + 2 Module）
- 搭建 iOS 壳工程（Xcode project + Podfile + SwiftUI wrapper）
- 搭建 H5 壳工程（JS target + webpack）
- 搭建微信小程序壳工程（miniApp + miniapp render）
- 配置全平台 targets（Android/iOS/H5/微信小程序）
- 填写需求文档（`docs/meta/project-requirements.md`）
- 更新规则文件与版本配置

### 待完成
| 编号 | 事项 | 说明 |
|------|------|------|
| P0-9 | Kuikly MCP（待确认） | 官方页面上有介绍但无安装方式，需关注后续发布 |

---

## Phase 1: 基础设施搭建 [待开始]

- ~~配置 Gradle wrapper~~ → 已移至 P0
- ~~配置 Kuikly KSP 插件~~ → 已移至 P0
- Kuikly Compose DSL 页面开发环境搭建
- 确认 Tencent Maven 仓库可达
- 确认 `shared` 模块 KSP 代码生成正常
- androidApp 完整集成（5 个必需适配器：图片/日志/异常/路由/线程）
- 验证 Android 端编译通过（若 P0 未完成）
- 创建 iOS 壳工程（Xcode project + Podfile）
- 验证 iOS 端编译通过
- 验证 AI 规则文件 `paths` 生效
- CI 配置（可选：GitHub Actions 基础检查）

---

## Phase 2: OCR 核心引擎

### 2.1 OCR 服务接口层
- 完善 `OCRService` 接口定义（参数、回调、异常）
- 定义图像预处理流程（裁剪、旋转、灰度化）

### 2.2 Android OCR 实现
- 集成 ML Kit Text Recognition 或 ONNX Runtime
- 实现 `OCRServiceAndroid`（`androidMain`）
- 相机权限处理

### 2.3 iOS OCR 实现
- 集成 Vision `VNRecognizeTextRequest`
- 实现 `OCRServiceIOS`（`iosMain`）
- 相机权限处理

### 2.4 图像获取
- 相机拍照页面（CameraView 组件）
- 相册选取页面
- 图像裁剪组件（ImageCropper）

---

## Phase 3: UI 页面开发

### 3.1 首页（HomePage）
- 拍照/选图入口
- 最近文档列表
- 空状态占位

### 3.2 预览页（PreviewPage）
- 图像展示
- OCR 识别触发
- 识别过程状态（loading / 进度 / 错误）

### 3.3 编辑页（EditorPage）
- 识别结果展示（文本块 overlay）
- 文本编辑/校对
- 排版调整

### 3.4 历史页（HistoryPage）
- 历史记录列表（按时间倒序）
- 搜索/筛选
- 删除/清空

---

## Phase 4: 数据持久化

- `StorageService` 实现（SharedPreferences / 本地文件）
- 历史记录增删改查
- 草稿自动保存与恢复
- 导出功能（纯文本 / Markdown / PDF）

---

## Phase 5: 平台适配与打磨

### 5.1 Android 适配
- 各屏幕尺寸适配
- 相机/存储权限适配（Android 13+ 细粒度权限）
- 主题风格统一

### 5.2 iOS 适配
- Xcode 项目配置（若 P1 未完成）
- CocoaPods 集成（若 P1 未完成）
- 安全区域、刘海屏适配
- 主题风格统一

### 5.3 通用打磨
- 动画过渡效果
- 错误处理与用户提示
- 加载状态骨架屏
- 无障碍支持

---

## Phase 6: 测试与质量

- 单元测试（model、service、util）
- 各页面 UI 交互测试
- OCR 准确率测试（各平台样本集）
- 性能测试（识别速度、内存占用）
- 多机型/多系统版本兼容性测试

---

## Phase 7: 发布准备

- App 图标、启动屏
- 应用商店资料（名称、描述、截图）
- Android: 生成签名 APK/AAB
- iOS: Archive + App Store Connect 配置
- 隐私政策（相机权限说明）
- 版本号管理规范

---

## 版本兼容性参照

依据官方文档（`https://kuikly.tds.qq.com/DevGuide/version_skills.html`）：

| Kotlin | AGP | KSP | Gradle |
|--------|-----|-----|--------|
| **2.1.21** | **8.13.2** | **2.1.21-2.0.1** | **8.13** ← 本项目当前选用 |
| 2.1.21 | 8.5.0 | 2.1.21-2.0.1 | 8.7 |
| 2.0.21 | 8.5.0 | 2.0.21-1.0.27 | 8.7 |
| 1.9.22 | 7.4.2 | 1.9.22-1.0.17 | 7.5.1 |

## 里程碑汇总

| 里程碑 | 对应 Phase | 交付物 |
|--------|-----------|--------|
| M1: 项目骨架 | Phase 0-1 | 可编译的空项目（Android） |
| M2: 核心识别 | Phase 2 | 拍照→OCR→展示结果闭环 |
| M3: 完整功能 | Phase 3-4 | 所有页面可用、数据可持久化 |
| M4: 跨端就绪 | Phase 5 | Android/iOS 双端适配完成 |
| M5: 发布 | Phase 6-7 | 应用商店上架 |

## 依赖关系

```
Phase 0 ──→ Phase 1 ──→ Phase 2 ──→ Phase 3 ──→ Phase 4
                │            │            │
                │            └──── Phase 5 ──→ Phase 6 ──→ Phase 7
                └─── Phase 5 (平台适配可并行启动)
```

- Phase 0→1→2→3→4 为串行主线
- Phase 5 平台适配可在 Phase 2-3 期间并行推进
- Phase 6 测试贯穿 Phase 2-5
- Phase 7 为最终阶段
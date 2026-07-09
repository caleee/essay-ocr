# 项目依赖的二进制文件获取指南

> 本仓库不存储二进制文件。以下说明如何获取构建所需的二进制文件。

## Gradle Wrapper JAR

`gradle/wrapper/gradle-wrapper.jar` 是 `./gradlew` 命令的引导程序。

### 首次获取

```bash
# 安装 Gradle（如未安装）
brew install gradle

# 在项目根目录生成 wrapper（指定版本 8.7）
cd /path/to/essay-ocr
gradle wrapper --gradle-version=8.7
```

### 新 clone 项目后

```bash
# 如果 gradlew 已存在但 wrapper jar 缺失：
gradle wrapper --gradle-version=8.7
```

或者手动下载 jar 放入 `gradle/wrapper/`：

```bash
curl -sL "https://services.gradle.org/distributions/gradle-8.7-bin.zip" -o /tmp/gradle-8.7.zip
unzip -q /tmp/gradle-8.7.zip -d /tmp/
cp /tmp/gradle-8.7/lib/plugins/gradle-wrapper-8.7.jar gradle/wrapper/gradle-wrapper.jar
```

---

## 追加说明

需要其他二进制文件时，在此文档追加获取方式。格式：

```markdown
## 文件名

### 用途

### 获取方式
```
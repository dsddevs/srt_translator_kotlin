# 🎬 SRT Translator

**SRT Translator** is a Kotlin-based tool for automatic translation of `.srt` subtitle files from one language to another. Designed for video creators, localization teams, and developers, it enables fast and efficient subtitle translation using the free [MyMemory Translation API](https://mymemory.translated.net/).

---

## ✨ Features

- 🔁 Translate subtitles between any languages
- 🌍 Uses the free MyMemory Translation API
- ⚡ Asynchronous processing with Kotlin Coroutines
- 🧠 Intelligent subtitle parsing and validation
- 📦 Lightweight and easy to integrate
- 🔤 HTML entity decoding and text cleaning
- 📜 Clean output in standard `.srt` format

---

## 📦 Installation

Add the following to your `build.gradle.kts`:

```kotlin
plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("com.github.pemistahl:lingua:1.2.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("io.ktor:ktor-client-core:3.1.1")
    implementation("io.ktor:ktor-client-cio:3.1.1")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")
}
```

## 🚀 Usage
#### 1. Prepare your input .srt file
   Place your source subtitle file in the root directory or specify the path.

#### 2. Run the translator

```kotlin
suspend fun main() {
    val translator = SrtTranslationProcess(
        File("input_file.srt"),  // Input subtitle file
        File("output_file.srt"), // Output subtitle file
        "zh", // Source language (e.g., Chinese)
        "ru"  // Target language (e.g., Russian)
)
translator.start()
}
```

#### 3. Get the translated output
After execution, you'll find the translated subtitles in output_file.srt.


#### 🌐 Supported Languages
The tool supports any language pairs supported by MyMemory. Use ISO 639-1 codes, such as:

- en – English
- es – Spanish
- fr – French
- ru – Russian
- zh – Chinese
- de – German
- ja – Japanese

## ⚠️ Limitations
- 🔁 API Rate Limiting: MyMemory free API has limited quota and may throttle requests.
- ⏱️ Delay Between Requests: 1.5-second delay is added between chunks to avoid API blocking.
- 🔠 Max Chunk Size: 500 characters per translation chunk.

## 📁 Example
Input (input_file.srt)

```dbn-psql
1
00:00:01,000 --> 00:00:04,000
你好，欢迎来到我们的视频。

1
00:00:01,000 --> 00:00:04,000
Привет, добро пожаловать в наше видео.
```

## 📁 Project Structure

```dbn-psql
src/
 └── main/
     └── kotlin/
         └── data/
            └── SubtitleData.kt
         └── srt/
            └── SrtContentBuilder.kt
            └── SrtLangHandler.kt
            └── SrtTranslationProcess.kt
         └── Main.kt
```

## ⚠️ Requirements
- JDK 21
- SRT file
- API mymemory.translated.net

## 📄 License
This project is licensed under the Apache-2.0 license
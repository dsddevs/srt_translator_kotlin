package org.example

import org.example.srt.SrtTranslationProcess
import java.io.File

suspend fun main() {
    val translator = SrtTranslationProcess(
        File("input_file.srt"),
        File("output_file.srt"),
        "zh",
        "ru"
    )

    translator.start()
}
package org.example.srt

import io.ktor.utils.io.charsets.Charsets
import org.example.data.SubtitleData
import java.io.File

class SrtTranslationProcess(
    private val inputFile: File,
    private val outputFile: File,
    private val inputLang: String,
    private val outputLang: String
) {

    private val langHandler = SrtLangHandler(this.inputLang, this.outputLang)
    private val content = SrtContentBuilder()

    suspend fun start()
    {
        try {
            val subtitles = parseSrtFile(inputFile.readText(Charsets.UTF_8))
            val translated = subtitles.map { subtitle ->
                val translatedText = subtitle.text.map { line ->
                    langHandler.translateText(line)
                }
                subtitle.copy(text = translatedText)
            }
            outputFile.writeText(content.buildSrtContent(translated))
            println("Translation completed successfully!")

        } finally {

        }
    }

    private fun parseSrtFile(content: String): List<SubtitleData> {
        return content
            .split("\n\n")
            .map { block -> block.trim() }
            .filter { it.isNotBlank() } // filter - empty blocks
            .mapNotNull { block ->
                val lines = block.split("\n").filter { it.isNotBlank() }
                if (lines.size < 2) {
                    println("Warning: Incorrect subtitles block passed: $block")
                    return@mapNotNull null
                }

                val number = lines.firstOrNull()?.toIntOrNull()
                if (number == null) {
                    println("Warning: Subtitle number parsing is not failure in block: $block")
                    return@mapNotNull null
                }

                val timeRange = lines.getOrNull(1)
                if (timeRange.isNullOrBlank()) {
                    println("Warning: Absent and incorrect of time range in block: $block")
                    return@mapNotNull null
                }

                val textLines = lines.drop(2)
                if (textLines.isEmpty()) {
                    println("Warning: Subtitle's text absent in block: $block")
                    return@mapNotNull null
                }

                SubtitleData(
                    number = number,
                    timeRange = timeRange,
                    text = textLines
                )
            }
    }


}
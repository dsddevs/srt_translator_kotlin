package org.example.srt

import org.example.data.SubtitleData

class SrtContentBuilder {

    fun buildSrtContent(data: List<SubtitleData>): String {
        return data.joinToString("\n\n") { content ->
            listOf(
                content.number.toString(),
                content.timeRange,
                content.text.joinToString("\n")
            ).joinToString("\n")
        }
    }

}
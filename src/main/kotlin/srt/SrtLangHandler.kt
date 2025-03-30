package org.example.srt

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.apache.commons.text.StringEscapeUtils

private const val MAX_CHUNK_LENGTH = 500
fun splitText(text: String) = text.chunked(MAX_CHUNK_LENGTH)


fun cleanText(text: String): String {
    val decodedHtmlText = StringEscapeUtils.unescapeHtml4(text)
    return decodedHtmlText.replace("\\s+".toRegex(), " ")
}


class SrtLangHandler(
    private val inputLang: String,
    private val outputLang: String
) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun translateText(text: String): String {
        if (text.isBlank()) return text

        return splitText(text)
            .map { translateChunk(it) }
            .joinToString(" ")
            .let(::cleanText)
    }

    private suspend fun translateChunk(chunk: String): String {
        delay(1500)

        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.mymemory.translated.net"
                path("get")
                parameters.append("q", chunk)
                parameters.append("langpair", "$inputLang|$outputLang")
            }
        }

        return try {
            val responseText = response.body<String>()
            val json = Json.parseToJsonElement(responseText).jsonObject

            json["responseData"]
                ?.jsonObject
                ?.get("translatedText")
                ?.jsonPrimitive
                ?.content
                ?.takeIf { it.isNotBlank() }
                ?: chunk

        } catch (e: Exception) {
            throw RuntimeException("Error: Translation chunk is not available - ${e.message}")
        }
    }

    fun close() = client.close()
}
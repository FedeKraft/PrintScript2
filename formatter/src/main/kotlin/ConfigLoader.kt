package org.example.util

import FormatterConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object ConfigLoader {
    fun loadConfig(filePath: String): FormatterConfig {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(File(filePath))
    }
}

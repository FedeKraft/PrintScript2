package org.example.util

import LinterConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object LinterConfigLoader {
    fun loadConfig(filePath: String): LinterConfig {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(File(filePath))
    }
}

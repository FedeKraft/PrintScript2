package formatter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import formatter.config.FormatterConfig
import java.io.File

object FormatterConfigLoader {
    fun loadConfig(filePath: String): FormatterConfig {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(File(filePath))
    }
}

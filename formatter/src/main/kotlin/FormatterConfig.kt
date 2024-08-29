
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class FormatterConfig(val rules: List<FormatterRuleConfig>)

@Serializable
data class FormatterRuleConfig(
    val type: String,
    val spaceBefore: Boolean,
    val spaceAfter: Boolean,
    val char: Char
)

fun loadFormatterConfig(filePath: String): FormatterConfig {
    val fileContent = File(filePath).readText()
    return Json.decodeFromString(fileContent)
}

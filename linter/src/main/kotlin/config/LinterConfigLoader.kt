package config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import linter.Linter
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import java.io.File
import java.io.InputStream


// This tells Jackson to ignore any fields in the config file that aren't declared here
@JsonIgnoreProperties(ignoreUnknown = true)
data class RuleConfig(
    val enabled: Boolean,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Config(
    val printSimpleExpression: RuleConfig = RuleConfig(false), // Default: disabled
    val snakeCaseIdentifier: RuleConfig = RuleConfig(false), // Default: disabled
    val camelCaseIdentifier: RuleConfig = RuleConfig(false), // Default: disabled
)

class LinterConfigLoader(
    private val astProvider: ASTProvider,
    configInputStream: InputStream? = null,
    configFilePath: String? = null,
) {

    private val config: Config

    init {
        val mapper = jacksonObjectMapper()

        config = try {
            if (configInputStream != null) {
                mapper.readValue(configInputStream)
            } else if (configFilePath != null) {
                mapper.readValue(File(configFilePath))
            } else {
                throw IllegalArgumentException("Either InputStream or FilePath must be provided.")
            }
        } catch (e: Exception) {
            println("Error al cargar el archivo de configuración: ${e.message}")
            Config() // Use default configuration if there's an error
        }
    }

    fun load(): Linter {
        val camelCaseRule = CamelCaseIdentifierRule(isActive = config.camelCaseIdentifier.enabled)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = config.snakeCaseIdentifier.enabled)
        val printRule = PrintSimpleExpressionRule(isActive = config.printSimpleExpression.enabled)

        // Logs for debugging rule states
        println("CamelCase Rule isActive: ${camelCaseRule.isActive}")
        println("SnakeCase Rule isActive: ${snakeCaseRule.isActive}")
        println("PrintSimpleExpression Rule isActive: ${printRule.isActive}")

        val rules = listOf(camelCaseRule, snakeCaseRule, printRule)
        return Linter(rules, astProvider)
    }
}

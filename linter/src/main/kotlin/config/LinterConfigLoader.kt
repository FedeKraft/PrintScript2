package config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import linter.Linter
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import java.io.InputStream

class LinterConfigLoader(private val astProvider: ASTProvider, configInputStream: InputStream) {

    data class RuleConfig(
        val enabled: Boolean,
    )

    // Cambiar la forma en que se leen los campos para permitir que falten
    data class Config(
        val printSimpleExpression: RuleConfig = RuleConfig(false), // Desactivado por defecto si falta
        val snakeCaseIdentifier: RuleConfig = RuleConfig(false), // Desactivado por defecto si falta
        val camelCaseIdentifier: RuleConfig = RuleConfig(false), // Desactivado por defecto si falta
    )

    private val config: Config

    init {
        val mapper = jacksonObjectMapper()
        config = try {
            mapper.readValue(configInputStream) // Leer desde InputStream en lugar de archivo
        } catch (e: Exception) {
            println("Error al cargar el archivo de configuración: ${e.message}")
            Config() // Usar configuración por defecto si hay error
        }
    }

    fun load(): Linter {
        val camelCaseRule = CamelCaseIdentifierRule(isActive = config.camelCaseIdentifier.enabled)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = config.snakeCaseIdentifier.enabled)
        val printRule = PrintSimpleExpressionRule(isActive = config.printSimpleExpression.enabled)

        // Logs para depurar el estado de las reglas
        println("CamelCase Rule isActive: ${camelCaseRule.isActive}")
        println("SnakeCase Rule isActive: ${snakeCaseRule.isActive}")
        println("PrintSimpleExpression Rule isActive: ${printRule.isActive}")

        val rules = listOf(camelCaseRule, snakeCaseRule, printRule)
        return Linter(rules, astProvider)
    }
}

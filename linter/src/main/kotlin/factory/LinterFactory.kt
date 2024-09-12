package factory

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import config.Config
import linter.Linter
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.ReadInputWithSimpleArgumentRule
import rules.SnakeCaseIdentifierRule
import java.io.InputStream

class LinterFactory {

    fun createLinter1_0(astProvider: ASTProvider, configInputStream: InputStream): Linter {
        val mapper = jacksonObjectMapper()
        val config: Config = try {
            mapper.readValue(configInputStream)
        } catch (e: Exception) {
            println("Error al cargar el archivo de configuración: ${e.message}")
            Config() // Usa la configuración por defecto en caso de error
        }

        val camelCaseRule = CamelCaseIdentifierRule(isActive = config.identifier_format == "camel case")
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = config.identifier_format == "snake case")
        val printRule = PrintSimpleExpressionRule(isActive = config.printSimpleExpression.mandatoryVariableOrLiteral)

        // Logs for debugging rule states
        println("CamelCase Rule isActive: ${camelCaseRule.isActive}")
        println("SnakeCase Rule isActive: ${snakeCaseRule.isActive}")
        println("PrintSimpleExpression Rule isActive: ${printRule.isActive}")

        val rules = listOf(camelCaseRule, snakeCaseRule, printRule)
        return Linter(rules, astProvider)
    }

    fun createLinter1_1(astProvider: ASTProvider): Linter {
        val rules =
            listOf(
                CamelCaseIdentifierRule(),
                SnakeCaseIdentifierRule(),
                PrintSimpleExpressionRule(),
                ReadInputWithSimpleArgumentRule(),
            )
        return Linter(rules, astProvider)
    }
}

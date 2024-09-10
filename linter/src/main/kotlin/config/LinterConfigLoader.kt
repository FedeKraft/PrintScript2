package config

import linter.Linter
import org.example.parser.ASTProvider
import rules.*

class LinterConfigLoader(private val astProvider: ASTProvider) {
    fun load(): Linter {
        // Puedes definir qué reglas están activas o desactivadas aquí
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = false)
        val printRule = PrintSimpleExpressionRule(isActive = true)

        val rules = listOf(camelCaseRule, snakeCaseRule, printRule)

        return Linter(rules, astProvider)
    }
}

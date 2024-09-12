package factory

import linter.Linter
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.ReadInputWithSimpleArgumentRule
import rules.SnakeCaseIdentifierRule

class LinterFactory {

    fun createLinter1_0(astProvider: ASTProvider): Linter {
        val rules = listOf(
            CamelCaseIdentifierRule(),
            PrintSimpleExpressionRule(),
        )
        return Linter(rules, astProvider)
    }

    fun createLinter1_1(astProvider: ASTProvider): Linter {
        val rules = listOf(
            CamelCaseIdentifierRule(),
            SnakeCaseIdentifierRule(),
            PrintSimpleExpressionRule(),
            ReadInputWithSimpleArgumentRule(),
        )
        return Linter(rules, astProvider)
    }
}

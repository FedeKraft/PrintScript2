/*
package commands

import Linter
import LinterError
import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.VariableDeclarationStatementCommand
import lexer.Lexer
import org.example.command.AssignationCommand
import org.example.parser.Parser
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import token.TokenType

class AnalyzingCommand : CliktCommand(help = "Analyze the file") {
    private val file by argument(help = "Source file to analyze")
    override fun run() {


        private val config = LinterConfigLoader.loadConfig()

        private val rules = listOf(
            CamelCaseIdentifierRule(config.camelCaseIdentifier.enabled),
            SnakeCaseIdentifierRule(config.snakeCaseIdentifier.enabled),
            PrintSimpleExpressionRule(config.printSimpleExpression.enabled)
        )



        val sourceCode = readSourceCodeFromFile("argument.txt")
        val lexer = Lexer(sourceCode)
        val parser = Parser(
            lexer, mapOf(
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            )
        )

        val linter = Linter(rules, parser)

        var errors = emptyList<LinterError>()
        while (true) {
            val error = linter.lint().firstOrNull() ?: break
            errors += error
        }

    }
}
/*
 */
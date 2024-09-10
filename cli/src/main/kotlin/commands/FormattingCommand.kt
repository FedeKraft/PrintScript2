package commands

import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import command.VariableDeclarationParser
import formatter.Formatter
import formatter.FormatterConfigLoader
import lexer.Lexer
import command.AssignationParser
import parser.ParserDirector
import rules.SpaceAroundEqualsRule
import token.TokenType
import java.io.File

class FormattingCommand : CliktCommand(help = "Format the file") {
    private val file by argument(help = "Source file to format")
    private val configFile by option(help = "Configuration file for formatting")

    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rules = listOf(
        SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    override fun run() {
        val sourceCode = readSourceCodeFromFile(file)
        val lexer = Lexer(sourceCode, patternsMap)
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

        val formatter = Formatter(rules, parserDirector)
        var result = ""
        for (formattedString in formatter.format()) {
            if (parserDirector.hasNextAST()) {
                result += formattedString.plus("\n")
            } else {
                result += formattedString
            }
        }
    }
}

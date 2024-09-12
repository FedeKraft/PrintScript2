package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import reader.Reader
import rules.SpaceAroundEqualsRule
import token.TokenType
import java.io.File

class FormattingCommand : CliktCommand(help = "Format the file") {
    private val file by argument(help = "Source file to format")
    private val configFile by option(help = "Configuration file for formatting")

    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rules =
        listOf(
            SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
        )

    private fun readSourceCodeFromFile(filename: String): String =
        File("src/test/resources/$filename").readText().replace(
            "\r\n",
            "\n",
        )

    override fun run() {
        val sourceCode = readSourceCodeFromFile(file)
        val lexer = LexerFactory().createLexer1_0(Reader(File(sourceCode).inputStream()))
        val parserDirector =
            ParserDirector(
                lexer,
                mapOf(
                    TokenType.PRINT to PrintParser(),
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

package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import factory.LexerFactory
import parser.ParserDirector
import reader.Reader
import token.TokenType
import java.io.File

class ValidationCommand : CliktCommand(help = "Validate the syntax and semantics of the file") {
    private val version by option(help = "Version of the language").default("1.0")
    private val file by argument(help = "Source file to validate")

    override fun run() {
        val reader = Reader(File(file).inputStream())
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = ParserDirector(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.PRINT to PrintParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )
        var statement = parser.nextStatement()
        while (statement != null) {
            statement = parser.nextStatement()
        }
        println("file validated")
    }
}

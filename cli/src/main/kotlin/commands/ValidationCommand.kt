package commands

import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.VariableDeclarationParser
import factory.LexerFactory
import command.AssignationParser
import parser.ParserDirector
import token.TokenType
import java.io.File

class ValidationCommand : CliktCommand(help = "Validate the syntax and semantics of the file") {
    private val file by argument(help = "Source file to validate")
    override fun run() {
        var code = File(file).readText()
        val lexer = LexerFactory().createLexer1_0(code)
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )
        var statement = parserDirector.nextStatement()
        while (statement != null) {
            println(statement)
            statement = parserDirector.nextStatement()
        }

        println("file validated")
    }
}

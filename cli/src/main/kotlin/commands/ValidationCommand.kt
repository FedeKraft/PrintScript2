package commands

import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.VariableDeclarationStatementCommand
import factory.LexerFactory
import org.example.command.AssignationCommand
import org.example.parser.Parser
import token.TokenType
import java.io.File

class ValidationCommand : CliktCommand(help = "Validate the syntax and semantics of the file") {
    private val file by argument(help = "Source file to validate")
    override fun run() {
        var code = File(file).readText()
        val lexer = LexerFactory().createLexer1_0(code)
        val parser = Parser(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            ),
        )
        var statement = parser.nextStatement()
        while (statement != null) {
            println(statement)
            statement = parser.nextStatement()
        }

        println("file validated")
    }
}

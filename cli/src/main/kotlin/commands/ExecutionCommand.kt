package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import factory.LexerFactory
import inputProvider.TestInputProvider
import interpreter.Interpreter
import parser.ParserDirector
import reader.Reader
import token.TokenType
import java.io.File

class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")
    override fun run() {
        val sourceCode = File(file).readText()
        val lexer = LexerFactory().createLexer1_0(Reader(File(sourceCode).inputStream()))
        val parser = ParserDirector(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.PRINT to PrintParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )
        val interpreter = Interpreter(parser, TestInputProvider("Hola"))
        interpreter.interpret()
        println("file executed")
    }
}

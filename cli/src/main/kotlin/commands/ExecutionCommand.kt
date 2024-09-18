package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import interpreter.Interpreter
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import provider.TestInputProvider
import reader.Reader
import token.TokenType
import java.io.File

class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")

    override fun run() {
        val sourceCode = File(file).readText()
        val lexer = LexerFactory().createLexer1_0(Reader(File(sourceCode).inputStream()))
        val parser =
            ParserDirector(
                lexer,
                mapOf(
                    TokenType.LET to VariableDeclarationParser(),
                    TokenType.PRINT to PrintParser(),
                    TokenType.IDENTIFIER to AssignationParser(),
                ),
            )
        val interpreter = Interpreter(parser, TestInputProvider("Hola"), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
        println("file executed")
    }
}

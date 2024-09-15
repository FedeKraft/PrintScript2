package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import interpreter.Interpreter
import parser.ParserDirector
import provider.ConsoleInputProvider
import reader.Reader
import token.TokenType
import java.io.File

class ExecutionCommand : CliktCommand(name = "execute", help = "Execute the file") {

    // Version option
    private val version by option(help = "Version of the language").default("1.0")

    // File argument
    private val file by argument(help = "Source file to execute")

    override fun run() {
        // Read the file
        val reader = Reader(File(file).inputStream())

        // Select lexer and parser version
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

        // Interpreter setup
        val interpreter = Interpreter(parser, ConsoleInputProvider(), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
        println("File executed using version $version")
    }
}

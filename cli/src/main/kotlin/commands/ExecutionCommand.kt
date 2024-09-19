package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import interpreter.Interpreter
import parser.ParserFactory
import provider.ConsoleInputProvider
import reader.Reader
import java.io.File

class ExecutionCommand : CliktCommand(name = "execute", help = "Execute the file") {

    // Version option
    private val version by option(help = "Version of the language").default("1.0")

    // File argument
    private val file by argument(help = "Source file to execute")

    override fun run() {
        val reader = Reader(File(file).inputStream())

        // lexer and parser version
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }

        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        // Interpreter
        val interpreter = Interpreter(parser, ConsoleInputProvider(), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
        println("File executed using version $version")
    }
}

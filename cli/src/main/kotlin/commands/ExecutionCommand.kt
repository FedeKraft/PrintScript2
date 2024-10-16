package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import interpreter.Interpreter
import factory.ParserFactory
import provider.ConsoleInputProvider
import reader.Reader
import java.io.File

class ExecutionCommand : CliktCommand(name = "execute", help = "Execute the file") {

    // Version option
    private val version by option(help = "Version of the language").default("1.0")

    // File argument
    private val file by argument(help = "Source file to execute")

    override fun run() {
        // Step 1: Process the file input
        val reader = Reader(File(file).inputStream())

        // Step 2: Create lexer and parser based on the language version
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }

        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        // Step 3: Initialize and run the interpreter, handling console input as needed
        val interpreter = Interpreter(
            provider = parser,
            inputProvider = ConsoleInputProvider(),  // Handles user input dynamically
            printEmitter = PrintEmitter(),
            errorCollector = ErrorCollector()
        )
        interpreter.interpret()

        // Final message indicating successful file execution
        println("File executed using version $version")
    }
}

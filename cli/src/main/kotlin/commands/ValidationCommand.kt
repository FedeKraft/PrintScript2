package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import factory.LexerFactory
import factory.ParserFactory
import reader.Reader
import java.io.File

class ValidationCommand : CliktCommand(name = "validate", help = "Validate the syntax and semantics of the file") {
    private val version by option(help = "Version of the language").default("1.0")
    private val file by argument(help = "Source file to validate")

    override fun run() {
        val reader = Reader(File(file).inputStream())
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        // Process statements while there are tokens
        while (parser.hasNextAST()) {
            val statement = parser.nextStatement()
            println(statement)
        }

        println("file validated")
    }
}

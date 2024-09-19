package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import factory.LexerFactory
import factory.LinterFactory
import parser.ParserFactory
import reader.Reader
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class AnalyzingCommand : CliktCommand(name = "analyze", help = "Analyze the file") {

    private val file by argument(help = "Source file to analyze")
    private val version by option(help = "Version of the language").default("1.0")
    private val configFilePath =
        "C:\\Users\\vranc\\Projects\\Ingsis\\PrintScript2\\cli\\src\\main\\resources\\linter-config.json"

    override fun run() {
        val reader = Reader(File(file).inputStream())
        val path = Paths.get(configFilePath)
        val inputStream: InputStream = Files.newInputStream(path)

        // Lexer
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }

        val parserDirector = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        val linter = when (version) {
            "1.1" -> LinterFactory().createLinter1_1(parserDirector, inputStream)
            else -> LinterFactory().createLinter1_0(parserDirector, inputStream)
        }

        println(linter.lint().toList())
    }
}

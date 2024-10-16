package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import factory.ParserFactory
import reader.Reader
import rules.Indentation
import rules.NewlineBeforePrintlnRule
import rules.NoSpaceAroundEqualsRule
import rules.SpaceAfterColonRule
import rules.SpaceBeforeColonRule
import java.io.File

class FormattingCommand : CliktCommand(name = "format", help = "Format the file") {

    private val file by argument(help = "Source file to format")
    private val version by option(help = "Version of the language").default("1.0")
    private val configFilePath =
        "C:\\Users\\vranc\\Projects\\Ingsis\\PrintScript2\\cli\\src\\main\\resources\\formatter-config.json"

    override fun run() {
        val reader = Reader(File(file).inputStream())

        // Load the configuration file
        val config = FormatterConfigLoader.loadConfig(configFilePath)

        // Define the formatting rules based on configuration
        val rules = listOf(
            Indentation(config.indentation),
            NoSpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
            SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
            SpaceAfterColonRule(config.spaceAfterColon.enabled),
            // Add other rules based on the provided config
            NewlineBeforePrintlnRule(config.newlineBeforePrintln),
        )

        // Lexer
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }

        val parserDirector = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        // Format the source code
        val formatter = Formatter(rules, parserDirector)

        File(file).bufferedWriter().use { writer ->
            for (formattedString in formatter.format()) {
                writer.write(formattedString)
                writer.newLine()
            }
        }
    }
}

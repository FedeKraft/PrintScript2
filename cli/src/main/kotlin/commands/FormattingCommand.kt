package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import parser.ParserDirector
import reader.Reader
import rules.SpaceAfterColonRule
import rules.SpaceAroundEqualsRule
import rules.SpaceAroundOperatorsRule
import rules.SpaceBeforeColonRule
import token.TokenType
import java.io.File

class FormattingCommand : CliktCommand(name = "format", help = "Format the file") {

    private val file by argument(help = "Source file to format")
    private val configFile by option(
        help = "C:\\Users\\vranc\\Projects\\Ingsis\\PrintScript2\\cli\\src\\main\\resources\\formatter-config.json\n",
    )

    override fun run() {
        val sourceCode = File(file).readText()

        // Cargar el archivo de configuración
        val config = configFile?.let {
            FormatterConfigLoader.loadConfig(it)
        } ?: FormatterConfigLoader.loadConfig(javaClass.getResource("/formatter-config.json").path)

        // Reglas de formateo basadas en la configuración
        val rules = listOf(
            SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
            SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
            SpaceAfterColonRule(config.spaceAfterColon.enabled),
            SpaceAroundOperatorsRule(),
        )

        // Lexer
        val lexer = LexerFactory().createLexer1_0(Reader(sourceCode.byteInputStream()))

        // ParserDirector
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

        // Formatear el código fuente
        val formatter = Formatter(rules, parserDirector)
        var result = ""
        for (formattedString in formatter.format()) {
            result += formattedString.plus("\n")
        }

        // Imprimir el resultado formateado
        println(result)
    }
}

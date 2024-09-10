import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import parser.ParserDirector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import rules.SpaceAfterColonRule
import rules.SpaceAroundEqualsRule
import rules.SpaceAroundOperatorsRule
import rules.SpaceBeforeColonRule
import token.TokenType
import java.io.File

class FormatterTest2 {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rules = listOf(
        SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
        SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
        SpaceAroundOperatorsRule(),
        SpaceAfterColonRule(enabled = config.spaceAfterColon.enabled),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test SpaceAroundEqualsRule with formatterTest2`() {
        val expected = readSourceCodeFromFile("formatterTest2Expected.txt")
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/formatterTest2.txt"))
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

        val formatter = Formatter(rules, parserDirector)
        var result = ""
        for (formattedString in formatter.format()) {
            if (parserDirector.hasNextAST()) {
                result += formattedString.plus("\n")
            } else {
                result += formattedString
            }
        }

        println("Generated output:\n$result")
        println("Expected output:\n$expected")
        assertEquals(expected, result)
    }
}

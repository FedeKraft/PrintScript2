import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.ParserDirector
import reader.Reader
import rules.Indentation
import rules.NewlineBeforePrintlnRule
import rules.SpaceAfterColonRule
import rules.SpaceAroundEqualsRule
import rules.SpaceBeforeColonRule
import token.TokenType
import java.io.ByteArrayInputStream
import java.io.File

class SpaceAroundEquals {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rulesEnabled = listOf(
        Indentation(config.indentation),
        SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
        SpaceAfterColonRule(config.spaceAfterColon.enabled),
        NewlineBeforePrintlnRule(config.newlineBeforePrintln),
        SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/spaceAroundEquals/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test space around equals enabled`() {
        val expected = readSourceCodeFromFile("spaceAroundEqualsExpected.txt")
        val unformattedCode = readSourceCodeFromFile("spaceAroundEquals.txt").toByteArray()
        val inputStream = ByteArrayInputStream(unformattedCode)
        val lexer = LexerFactory().createLexer1_1(Reader(inputStream))
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

        val formatter = Formatter(rulesEnabled, parserDirector)
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

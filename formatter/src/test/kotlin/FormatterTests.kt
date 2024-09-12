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
import rules.SingleSpaceBetweenTokensRule
import rules.SpaceAfterColonRule
import rules.SpaceAroundEqualsRule
import rules.SpaceAroundOperatorsRule
import rules.SpaceBeforeColonRule
import token.TokenType
import java.io.ByteArrayInputStream
import java.io.File

class FormatterTests {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rulesEnabled = listOf(
        SingleSpaceBetweenTokensRule(),
        Indentation(config.indentation),
        SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
        SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
        SpaceAroundOperatorsRule(),
        SpaceAfterColonRule(config.spaceAfterColon.enabled),
        NewlineBeforePrintlnRule(config.newlineBeforePrintln),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test SpaceBeforeColonRule enabled`() {
        val expected = readSourceCodeFromFile("formatterTest2Expected.txt")
        val unformattedCode = readSourceCodeFromFile("formatterTest2.txt").toByteArray()
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

import factory.LexerFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.ConstDeclarationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import reader.Reader
import rules.Indentation
import rules.SpaceAfterColonRule
import token.TokenType
import java.io.ByteArrayInputStream
import java.io.File

class ColonTests {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rulesEnabled = listOf(
        Indentation(config.indentation),
        SpaceAfterColonRule(config.spaceAfterColon.enabled),

    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/colonTests/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test SpaceBeforeColonRule enabled`() {
        val expected = readSourceCodeFromFile("spaceAfterColonTestExpected.txt")
        val unformattedCode = readSourceCodeFromFile("spaceAfterColonTest.txt").toByteArray()
        val inputStream = ByteArrayInputStream(unformattedCode)
        val lexer = LexerFactory().createLexer1_1(Reader(inputStream))
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
                TokenType.CONST to ConstDeclarationParser(),
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

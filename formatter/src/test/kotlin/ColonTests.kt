import factory.FormatterFactory
import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.ConstDeclarationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import reader.Reader
import token.TokenType
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class ColonTests {

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

        val path = Paths.get("src/test/resources/formatter-config.json")
        val configFromJSON: InputStream = Files.newInputStream(path)
        val formatter = FormatterFactory().createFormatter1_1(parserDirector, configFromJSON)

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

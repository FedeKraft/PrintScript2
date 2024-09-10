import formatter.Formatter
import formatter.FormatterConfigLoader
import lexer.Lexer
import command.AssignationParser
import command.PrintParser
import command.VariableDeclarationParser
import parser.ParserDirector
import org.junit.jupiter.api.Test
import rules.SpaceAroundEqualsRule
import token.TokenType
import java.io.File

class FormatterTest1 {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rules = listOf(
        SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test SpaceAroundEqualsRule with formatterTest1`() {
        val sourceCode = readSourceCodeFromFile("formatterTest1.txt")
        val expected = readSourceCodeFromFile("formatterTest1Expected.txt")

        val lexer = Lexer(sourceCode)
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

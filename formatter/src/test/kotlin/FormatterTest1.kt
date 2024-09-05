import command.VariableDeclarationStatementCommand
import formatter.Formatter
import formatter.FormatterConfigLoader
import lexer.Lexer
import org.example.command.AssignationCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.Parser
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
        val parser = Parser(
            lexer,
            mapOf(
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            ),
        )

        val formatter = Formatter(rules, parser)
        var result = ""
        for (formattedString in formatter.format()) {
            if (parser.hasNextAST()) {
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

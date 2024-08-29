import org.example.Formatter
import org.example.Parser
import org.example.rules.FormatterRule
import org.example.rules.SingleSpaceBetweenTokensRule
import org.example.rules.SpaceAroundCharsRule
import org.example.rules.SpaceAroundSemicolonRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTest1 {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    private val rules1 = emptyList<FormatterRule>()
    private val rules2 = listOf(
        SpaceAroundCharsRule(spaceBefore = false, spaceAfter = true, ':'),
        SpaceAroundCharsRule(spaceBefore = true, spaceAfter = true, '='), SingleSpaceBetweenTokensRule(),
        SpaceAroundSemicolonRule()
    )

    @Test
    fun `test formatter with space around colon rule`() {
        val code = readSourceCodeFromFile("formatterTest1.txt")
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser()
        val ast = parser.parse(tokens)

        val formatter = Formatter(rules2)
        val formattedCode = formatter.format(ast, code)
        val expectedCode = readSourceCodeFromFile("SpaceAroundColonRuleTestExpected.txt")


        assertEquals(expectedCode, formattedCode)
    }

}

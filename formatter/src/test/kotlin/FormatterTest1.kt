import org.example.Formatter
import org.example.Parser
import org.example.rules.SpaceAroundCharsRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTest1 {

    private fun readSourceCodeFromFile(filename: String): StringBuilder {
        val code = File("src/test/resources/$filename").readText()
        return StringBuilder(code)
    }

    @Test
    fun `test formatter with space around colon rule`() {
        val code = readSourceCodeFromFile("formatterTest1.txt")
        val lexer = Lexer(code.toString())
        val tokens = lexer.tokenize()
        val parser = Parser()
        val ast = parser.parse(tokens)
        println(tokens)

        val formatter = Formatter(listOf(SpaceAroundCharsRule(spaceBefore = true, spaceAfter = true)))
        val formattedCode = formatter.format(ast,code.toString())
        println(formattedCode)

        val expectedCode = readSourceCodeFromFile("SpaceAroundColonRuleTestExpected.txt")
        assertEquals(expectedCode.toString(), formattedCode)
    }
}
import org.example.Formatter
import org.example.Parser
import org.example.util.ConfigLoader
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

    @Test
    fun `test formatter with space around colon rule`() {
        // Cargar la configuración desde el archivo JSON
        val config = ConfigLoader.loadConfig("src/test/resources/formatter-config.json")

        // Crear las reglas basadas en la configuración cargada
        val rules = listOf(
            SpaceAroundCharsRule(config.spaceAroundChars), // Usar config.spaceAroundChars
            SingleSpaceBetweenTokensRule(),
            SpaceAroundSemicolonRule()
        )

        // El código original del test
        val code = readSourceCodeFromFile("formatterTest1.txt")
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser()
        val ast = parser.parse(tokens)

        val formatter = Formatter(rules)
        val formattedCode = formatter.format(ast, code)
        val expectedCode = readSourceCodeFromFile("SpaceAroundColonRuleTestExpected.txt")

        // Validar que el código formateado es el esperado
        assertEquals(expectedCode, formattedCode)
    }
}

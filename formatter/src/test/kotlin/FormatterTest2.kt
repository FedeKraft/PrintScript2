import command.VariableDeclarationStatementCommand
import formatter.Formatter
import formatter.FormatterConfigLoader
import lexer.Lexer
import org.example.command.AssignationCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.*
import token.TokenType
import java.io.File

class FormatterTest2 {
    private val config = FormatterConfigLoader.loadConfig("src/test/resources/formatter-config.json")

    private val rules = listOf(
    SpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
    SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
    SpaceAroundOperatorsRule(),
    SpaceAfterColonRule(config.spaceAfterColon.enabled),
)

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }
    @Test
    fun `test SpaceAroundEqualsRule with formatterTest2`() {
        val sourceCode = readSourceCodeFromFile("formatterTest2.txt")
        val expected = readSourceCodeFromFile("formatterTest2Expected.txt")
        val lexer = Lexer(sourceCode, patternsMap)
        val parser = Parser(lexer, mapOf(
            TokenType.PRINT to PrintStatementCommand(),
            TokenType.LET to VariableDeclarationStatementCommand(),
            TokenType.IDENTIFIER to AssignationCommand(),
        ))

        val formatter = Formatter(rules, parser)
        var result = ""
        for (formattedString in formatter.format()) {
            if (parser.hasNextAST() ){
                result += formattedString.plus("\n")
            }
            else{
                result += formattedString
            }


        }

        println("Generated output:\n$result")
        println("Expected output:\n$expected")
        assertEquals(expected, result)
    }


}

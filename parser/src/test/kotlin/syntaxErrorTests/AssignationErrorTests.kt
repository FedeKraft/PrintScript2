import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.TokenType
import lexer.Lexer
import org.example.command.AssignationCommand
import java.io.File

class AssignationErrorTests {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test variable assignment syntax error, missing assign token`() {
        val code = readSourceCodeFromFile("AssignationErrors.txt").lines()[0] // Primera línea: x/5.0;
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Missing ASSIGN token in variable assignment line: 1, column: 3", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, initialize statement with number`() {
        val code = readSourceCodeFromFile("AssignationErrors.txt").lines()[1] // Segunda línea: =5.0;
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Syntax error, cannot initialize a statement with token: =, line: 1, column: 1", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, missing value`() {
        val code = readSourceCodeFromFile("AssignationErrors.txt").lines()[3] // Cuarta línea: x=;
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Missing value token after = in line: 1, column: 2", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, `() {
        val code = readSourceCodeFromFile("AssignationErrors.txt").lines()[4] // Quinta línea: 5=x;
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Syntax error, cannot initialize a statement with token: 5.0, line: 1, column: 1", message) // tira unknown command porque la linea arranca con un number
        }
    }

    @Test
    fun `test variable assignment syntax error, invalid value token`() {
        val code = readSourceCodeFromFile("AssignationErrors.txt").lines()[5] // Sexta línea: x=(;
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Invalid value token in variable assignment line: 1, column: 3", message)
        }
    }
}

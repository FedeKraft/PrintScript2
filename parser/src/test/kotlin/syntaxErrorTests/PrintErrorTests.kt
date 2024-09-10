import lexer.Lexer
import command.PrintStatementCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.TokenType
import java.io.File

class PrintErrorTests {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test print syntax error, invalid argument`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[0] // Línea con token desconocido
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Invalid argument in print statement line: 1, column: 7", message)
        }
    }

    @Test
    fun `test print syntax error, missing args`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[1]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Missing args in print statement line: 1, column: 6", message)
        }
    }

    @Test
    fun `test print syntax error, missing left parenthesis`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[2]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Print statement is missing a LEFT_PARENTHESIS token line: 1, column: 1", message)
        }
    }

    @Test
    fun `test print syntax error, missing right parenthesis`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[3]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Print statement is missing a RIGHT_PARENTHESIS token line: 1, column: 1", message)
        }
    }

    @Test
    fun `test print syntax error, invalid token order`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[4]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Unexpected token order in print statement line: 1, column: 6", message)
        }
    }

    @Test
    fun `test print syntax error, invalid number of arguments`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[5]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Invalid number of arguments in print statement line: 1, column: 12", message)
        }
    }

    @Test
    fun `test print syntax error, invalid argument type`() {
        val code = readSourceCodeFromFile("PrintErrors.txt").lines()[6]
        val lexer = Lexer(code)
        val commands = mapOf(TokenType.PRINT to PrintStatementCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Invalid argument in print statement line: 1, column: 7", message)
        }
    }
}

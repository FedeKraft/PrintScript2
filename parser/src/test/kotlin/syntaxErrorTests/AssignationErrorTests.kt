import factory.LexerFactory
import org.example.command.AssignationCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reader.Reader
import token.TokenType

class AssignationErrorTests {

    @Test
    fun `test variable assignment syntax error, missing assign token`() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/AssignationErrors.txt"))
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
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/AssignationErrors.txt"))
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
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/AssignationErrors.txt"))
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
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/AssignationErrors.txt"))
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals(
                "Syntax error, cannot initialize " +
                    "a statement with token: 5.0, line: 1, column: 1",
                message,
            ) // tira unknown command porque la linea arranca con un number
        }
    }

    @Test
    fun `test variable assignment syntax error, invalid value token`() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/AssignationErrors.txt"))
        val commands = mapOf(TokenType.IDENTIFIER to AssignationCommand())
        val parser = Parser(lexer, commands)

        assertThrows<RuntimeException> {
            parser.nextStatement()
        }.apply {
            assertEquals("Invalid value token in variable assignment line: 1, column: 3", message)
        }
    }
}

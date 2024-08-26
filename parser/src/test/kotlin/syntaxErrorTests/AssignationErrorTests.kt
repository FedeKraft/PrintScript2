import org.example.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AssignationErrorTests {

    @Test
    fun `test variable assignment syntax error, unknown token`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("/"), 1, 2),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 3),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 4),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Unknown token in print statement, line: 1, column: 2", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, missing identifier`() {
        val tokens = listOf(
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 1),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 2),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 3),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Token inesperado en linea 1, columna 1: ASSIGN", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, missing assign`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 2),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 2),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 3),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token ASSIGN in variable assignment", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, missing value (invalid number of tokens)`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 2),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 3),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid number of tokens in variable assignment", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, invalid token order`() {
        val tokens = listOf(
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 1),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 2),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 3),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 4),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Token inesperado en linea 1, columna 1: NUMBER", message)
        }
    }

    @Test
    fun `test variable assignment syntax error, invalid value token`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 2),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 3),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 4),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid value token in variable assignment", message)
        }
    }
}

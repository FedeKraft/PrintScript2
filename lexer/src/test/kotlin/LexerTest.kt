import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.Token
import token.TokenType
import token.TokenValue

class LexerTest {

    @Test
    fun `test lexer with errors`() {
        val reader = Reader("src/test/resources/StatementsWithErrors.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()

        var errorFound = false
        while (lexer.hasNextToken() && !errorFound) {
            val token = lexer.nextToken()
            actualTokens.add(token)
            if (token.type == TokenType.UNKNOWN) {
                errorFound = true
            }
        }

        actualTokens.forEach { println(it) }

        val expectedTokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 1, 9),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 9),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 16),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 17),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 25),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 25),

            Token(TokenType.LET, TokenValue.StringValue("let"), 2, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 2, 8),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 8),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 2, 15),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 16),
            Token(TokenType.NUMBER, TokenValue.NumberValue(21.0), 2, 19),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 19),

            Token(TokenType.PRINT, TokenValue.StringValue("print"), 3, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 3, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 3, 10),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 3, 10),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 10),

            Token(TokenType.LET, TokenValue.StringValue("let"), 4, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("sum"), 4, 8),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 4, 8),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 4, 15),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 4, 16),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 4, 18),
            Token(TokenType.SUM, TokenValue.StringValue("+"), 4, 19),
            Token(TokenType.NUMBER, TokenValue.NumberValue(3.0), 4, 21),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 4, 21),

            Token(TokenType.LET, TokenValue.StringValue("let"), 5, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("product"), 5, 12),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 5, 12),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 5, 19),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 5, 20),
            Token(TokenType.NUMBER, TokenValue.NumberValue(7.0), 5, 22),
            Token(TokenType.MULTIPLY, TokenValue.StringValue("*"), 5, 23),
            Token(TokenType.NUMBER, TokenValue.NumberValue(6.0), 5, 25),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 5, 25),

            Token(TokenType.LET, TokenValue.StringValue("let"), 6, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("difference"), 6, 15),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 6, 15),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 6, 22),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 6, 23),
            Token(TokenType.NUMBER, TokenValue.NumberValue(10.0), 6, 26),
            Token(TokenType.SUBTRACT, TokenValue.StringValue("-"), 6, 27),
            Token(TokenType.NUMBER, TokenValue.NumberValue(4.0), 6, 29),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 6, 29),

            Token(TokenType.LET, TokenValue.StringValue("let"), 7, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("quotient"), 7, 13),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 7, 13),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 7, 20),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 7, 21),
            Token(TokenType.NUMBER, TokenValue.NumberValue(8.0), 7, 23),
            Token(TokenType.DIVIDE, TokenValue.StringValue("/"), 7, 24),
            Token(TokenType.NUMBER, TokenValue.NumberValue(2.0), 7, 26),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 7, 26),

            Token(TokenType.PRINT, TokenValue.StringValue("print"), 8, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 8, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 8, 10),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("]"), 8, 10),
        )

        val minSize = minOf(expectedTokens.size, actualTokens.size)

        for (i in 0 until minSize) {
            val expected = expectedTokens[i]
            val actual = actualTokens[i]
            println("Comparing token at index $i:")
            println("Expected: $expected")
            println("Actual: $actual")

            assertEquals(expected.type, actual.type, "Token type at index $i does not match")

            if (expected.value is TokenValue.StringValue && actual.value is TokenValue.StringValue) {
                assertEquals(
                    (expected.value as TokenValue.StringValue).value,
                    (actual.value as TokenValue.StringValue).value,
                    "Token value at index $i does not match",
                )
            } else {
                assertEquals(expected.value, actual.value, "Token value at index $i does not match")
            }

            assertEquals(expected.line, actual.line, "Token line at index $i does not match")
            assertEquals(expected.column, actual.column, "Token column at index $i does not match")
        }

        if (expectedTokens.size != actualTokens.size) {
            println("Number of tokens do not match:")
            println("Expected: ${expectedTokens.size}, Actual: ${actualTokens.size}")
            if (expectedTokens.size > actualTokens.size) {
                println("Missing tokens: ${expectedTokens.drop(minSize)}")
            } else {
                println("Extra tokens: ${actualTokens.drop(minSize)}")
            }
        }
    }
}
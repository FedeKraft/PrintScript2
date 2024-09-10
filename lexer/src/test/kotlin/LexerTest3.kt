import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.Token
import token.TokenType
import token.TokenValue

class LexerTest3 {

    @Test
    fun `testing new lexer`() {
        val reader = Reader("src/test/resources/AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }

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
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 3, 9),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 3, 9),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 9),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 4, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 4, 5),
            Token(TokenType.NUMBER, TokenValue.NumberValue(22.0), 4, 8),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 4, 8),
        )
        // Comparar cada token en orden
        assertEquals(expectedTokens.size, actualTokens.size, "Number of tokens do not match")

        for (i in expectedTokens.indices) {
            val expected = expectedTokens[i]
            val actual = actualTokens[i]

            // Ver ambos tokens en detalle
            println("Comparing token at index $i:")
            println("Expected: $expected")
            println("Actual: $actual")

            assertEquals(expected.type, actual.type, "Token type at index $i does not match")
            assertEquals(expected.line, actual.line, "Token line at index $i does not match")
            assertEquals(expected.column, actual.column, "Token column at index $i does not match")
        }
    }

    @Test
    fun `test lexer partial lines`() {
        val reader = Reader("src/test/resources/AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(reader)

        // Function to lex a specified number of lines
        fun lexLines(numberOfLines: Int): List<Token> {
            val tokens = mutableListOf<Token>()
            var linesLexed = 0

            while (lexer.hasNextToken() && linesLexed < numberOfLines) {
                val token = lexer.nextToken()
                tokens.add(token)
                println(token)
                if (token.type == TokenType.SEMICOLON) {
                    linesLexed++
                }
            }
            return tokens
        }

        // Lex the first 2 lines
        val tokensFirstTwoLines = lexLines(3)

        println("Tokens from the first three lines: $tokensFirstTwoLines")
        println("Total tokens lexed: ${tokensFirstTwoLines.size}") // Imprime el total de tokens

        assertEquals(19, tokensFirstTwoLines.size)
    }

    @Test
    fun `test lexer with errors`() {
        val reader = Reader("src/test/resources/StatementsWithErrors.txt")
        val lexer = LexerFactory().createLexer1_0(reader)
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

            Token(TokenType.PRINT, TokenValue.StringValue("print"), 4, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 4, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 4, 9),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("]"), 4, 9),
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

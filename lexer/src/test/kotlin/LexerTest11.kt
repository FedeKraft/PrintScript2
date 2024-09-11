import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.Token
import token.TokenType
import token.TokenValue

class LexerTest11 {

    @Test
    fun `variable declaration 1_1`() {
        val reader = Reader("src/test/resources/VariableDeclarationTokens/VariableDeclarationTokens.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
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
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `print statement 1_1`() {
        val reader = Reader("src/test/resources/PrintTokens/PrintTokens.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 1, 9),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 9),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 9),

            Token(TokenType.PRINT, TokenValue.StringValue("print"), 2, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 2, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 2, 10),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 2, 10),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 10),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `assignation 1_1`() {
        val reader = Reader("src/test/resources/AssignationTokens/AssignationTokens.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 1, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
            Token(TokenType.NUMBER, TokenValue.NumberValue(22.0), 1, 8),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 8),

            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 2, 5),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 6),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 2, 14),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 14),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `operations 1_1`() {
        val reader = Reader("src/test/resources/BinaryOperationTokens/BinaryOperationTokens.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("sum"), 1, 8),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 8),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 1, 15),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 16),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 18),
            Token(TokenType.SUM, TokenValue.StringValue("+"), 1, 19),
            Token(TokenType.NUMBER, TokenValue.NumberValue(3.0), 1, 21),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 21),

            Token(TokenType.LET, TokenValue.StringValue("let"), 2, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("product"), 2, 12),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 12),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 2, 19),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 20),
            Token(TokenType.NUMBER, TokenValue.NumberValue(7.0), 2, 22),
            Token(TokenType.MULTIPLY, TokenValue.StringValue("*"), 2, 23),
            Token(TokenType.NUMBER, TokenValue.NumberValue(6.0), 2, 25),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 25),

            Token(TokenType.LET, TokenValue.StringValue("let"), 3, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("difference"), 3, 15),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 3, 15),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 3, 22),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 3, 23),
            Token(TokenType.NUMBER, TokenValue.NumberValue(10.0), 3, 26),
            Token(TokenType.SUBTRACT, TokenValue.StringValue("-"), 3, 27),
            Token(TokenType.NUMBER, TokenValue.NumberValue(4.0), 3, 29),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 29),

            Token(TokenType.LET, TokenValue.StringValue("let"), 4, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("quotient"), 4, 13),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 4, 13),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 4, 20),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 4, 21),
            Token(TokenType.NUMBER, TokenValue.NumberValue(8.0), 4, 23),
            Token(TokenType.DIVIDE, TokenValue.StringValue("/"), 4, 24),
            Token(TokenType.NUMBER, TokenValue.NumberValue(2.0), 4, 26),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 4, 26),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `test lexer with errors 1_1`() {
        val reader = Reader("src/test/resources/StatementWithError/StatementsWithErrors.txt")
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

            Token(TokenType.PRINT, TokenValue.StringValue("print"), 2, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 2, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 2, 10),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("]"), 2, 10),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `statement with braces`() {
        val reader = Reader("src/test/resources/BraceTokens/Braces.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()
        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.OPEN_BRACE, TokenValue.StringValue("{"), 1, 1),
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 4),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 1, 9),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 9),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 16),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 17),
            Token(TokenType.STRING, TokenValue.StringValue("Braces"), 1, 26),
            Token(TokenType.CLOSE_BRACE, TokenValue.StringValue("}"), 1, 26),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 26),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `const statements`() {
        val reader = Reader("src/test/resources/ConstTokens/ConstTokens.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()
        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.CONST, TokenValue.StringValue("const"), 1, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 1, 11),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 11),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 18),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 19),
            Token(TokenType.STRING, TokenValue.StringValue("Constantine"), 1, 33),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 33),

            Token(TokenType.CONST, TokenValue.StringValue("const"), 2, 6),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 2, 10),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 10),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 2, 17),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 18),
            Token(TokenType.NUMBER, TokenValue.NumberValue(14.0), 2, 21),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 21),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `else statements`() {
        val reader = Reader("src/test/resources/ElseTokens/Elses.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()
        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.ELSE, TokenValue.StringValue("else"), 1, 5),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 5),
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 10),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 10),
            Token(TokenType.STRING, TokenValue.StringValue("Bielsiada"), 1, 21),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 21),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 21),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 21),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `if statements 1_1`() {
        val reader = Reader("src/test/resources/IfTokens/Ifs.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens = listOf(
            Token(TokenType.IF, TokenValue.StringValue("if"), 1, 3),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 3),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("a"), 1, 4),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 4),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 4),

            Token(TokenType.IF, TokenValue.StringValue("if"), 2, 3),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 2, 3),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("b"), 2, 4),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 2, 4),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 4),

            Token(TokenType.IF, TokenValue.StringValue("if"), 3, 3),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 3, 3),
            Token(TokenType.NUMBER, TokenValue.NumberValue(1.0), 3, 4),
            Token(TokenType.SUM, TokenValue.StringValue("+"), 3, 4),
            Token(TokenType.NUMBER, TokenValue.NumberValue(1.0), 3, 5),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 3, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 5),
        )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }
}
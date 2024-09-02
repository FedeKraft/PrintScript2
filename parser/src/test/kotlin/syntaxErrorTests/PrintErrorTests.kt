package syntaxErrorTests

class PrintErrorTests {
/*
    @Test
    fun `test print syntax error, unknown token in statement`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 1),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("/"), 1, 7),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 12),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 13),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 19),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 20),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Unknown token in print statement, line: 1, column: 7", message)
        }
    }

    @Test
    fun `test print syntax error, missing args in statement`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 1),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 6),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 13),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 14),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing args in print statement", message)
        }
    }

    @Test
    fun `test print syntax error, missing left parenthesis token in statement`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 6),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 7),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 13),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 14),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token LEFT_PARENTHESIS in print statement", message)
        }
    }

    @Test
    fun `test print syntax error, missing right parenthesis token in statement`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 7),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 13),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 14),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token RIGHT_PARENTHESIS in print statement", message)
        }
    }

    @Test
    fun `test print syntax error, invalid token order`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 1),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 6),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 7),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 8),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 14),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Unexpected token order in print statement", message)
        }
    }

    @Test
    fun `test print syntax error, invalid number of arguments`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 1),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 6),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 7),
            Token(TokenType.SUM, TokenValue.StringValue("+"), 1, 13),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 14),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 15),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid number of arguments in print statement", message)
        }
    }

    @Test
    fun `test print syntax error, invalid argument type`() {
        val tokens = listOf(
            Token(TokenType.PRINT, TokenValue.StringValue("print"), 1, 1),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 6),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 7),
            Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 8),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 9),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid argument in print statement", message)
        }
    }

 */
}

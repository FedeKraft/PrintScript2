class VariableDeclarationErrorTests {
/*
    @Test
    fun `test variable declaration syntax error, unknown token`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 5),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 6),
            Token(TokenType.UNKNOWN, TokenValue.StringValue("/"), 1, 7),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 8),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 9),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 10),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Unknown token in print statement, linea 1, columna 7", message)
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, missing LET`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 2),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 3),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 6),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid number of tokens in variable assignment", message) // sin let es una asignacion
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, missing IDENTIFIER`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 2),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 3),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 6),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token IDENTIFIER in variable declaration", message)
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, missing COLON`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 3),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 6),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token COLON in variable declaration", message)
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, missing TYPE`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 6),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing type token in variable declaration", message)
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, missing ASSIGN`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 6),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Missing token ASSIGN in variable declaration", message)
        }
    }

    @Test
    fun `test variable declaration syntax error, invalid value token`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Expected value token, found LEFT_PARENTHESIS", message)
        }
    }

    @Test
    @Disabled
    fun `test variable declaration syntax error, LET not first`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 1),
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Invalid number of tokens in variable assignment", message)
        }
    }

    @Test
    fun `test variable declaration syntax error, IDENTIFIER not second`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 2),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 3),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Expected 'IDENTIFIER', found COLON", message)
        }
    }

    @Test
    fun `test variable declaration syntax error, COLON not third`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 3),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 4),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Expected ':', found STRING_TYPE", message)
        }
    }

    @Test
    fun `test variable declaration syntax error, TYPE not fourth`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 4),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 5),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Expected type 'STRING_TYPE' or 'NUMBER_TYPE', found ASSIGN", message)
        }
    }

    @Test
    fun `test variable declaration syntax error, ASSIGN not fifth`() {
        val tokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("x"), 1, 2),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 3),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 4),
            Token(TokenType.STRING, TokenValue.StringValue("hello"), 1, 5),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 6),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 7),
        )

        val parser = Parser()
        assertThrows<RuntimeException> {
            parser.parse(tokens)
        }.apply {
            assertEquals("Expected '=', found STRING", message)
        }
    }

 */
}

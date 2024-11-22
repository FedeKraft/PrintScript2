import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.Token
import token.TokenType
import token.TokenValue
import java.io.File

class LexerTest10 {
    @Test
    fun `variable declaration 1_0`() {
        val reader =
            Reader(File("src/test/resources/VariableDeclarationTokens/VariableDeclarationTokens.txt").inputStream())
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens =
            listOf(
                Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 1, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 9),
                Token(TokenType.STRING_TYPE, TokenValue.StringValue("string"), 1, 11),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 18),
                Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 20),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 27),
                Token(TokenType.LET, TokenValue.StringValue("let"), 2, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 2, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 8),
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("number"), 2, 10),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 17),
                Token(TokenType.NUMBER, TokenValue.NumberValue(21.0), 2, 19),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 21),
            )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `print statement 1_0`() {
        val reader = Reader(File("src/test/resources/PrintTokens/PrintTokens.txt").inputStream())
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens =
            listOf(
                Token(TokenType.PRINT, TokenValue.StringValue("println"), 1, 1),
                Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 1, 8),
                Token(TokenType.STRING, TokenValue.StringValue("Hello: "), 1, 9),
                Token(TokenType.SUM, TokenValue.StringValue("+"), 1, 19),
                Token(TokenType.NUMBER, TokenValue.NumberValue(3.0), 1, 21),
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 1, 22),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 23),
                Token(TokenType.PRINT, TokenValue.StringValue("println"), 2, 1),
                Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 2, 8),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 2, 9),
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 2, 13),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 14),
            )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `assignation 1_0`() {
        val reader = Reader(File("src/test/resources/AssignationTokens/AssignationTokens.txt").inputStream())
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens =
            listOf(
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 1, 1),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 5),
                Token(TokenType.NUMBER, TokenValue.NumberValue(22.0), 1, 7),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 9),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 2, 1),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 6),
                Token(TokenType.STRING, TokenValue.StringValue("Olive"), 2, 8),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 15),
            )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }

    @Test
    fun `operations 1_0`() {
        val reader = Reader(File("src/test/resources/BinaryOperationTokens/BinaryOperationTokens.txt").inputStream())
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            actualTokens.add(lexer.nextToken())
        }
        val expectedTokens =
            listOf(
                Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("sum"), 1, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 8),
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("number"), 1, 10),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 17),
                Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 1, 19),
                Token(TokenType.SUM, TokenValue.StringValue("+"), 1, 21),
                Token(TokenType.NUMBER, TokenValue.NumberValue(3.0), 1, 23),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 24),
                Token(TokenType.LET, TokenValue.StringValue("let"), 2, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("product"), 2, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 12),
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("number"), 2, 14),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 21),
                Token(TokenType.NUMBER, TokenValue.NumberValue(7.0), 2, 23),
                Token(TokenType.MULTIPLY, TokenValue.StringValue("*"), 2, 25),
                Token(TokenType.NUMBER, TokenValue.NumberValue(6.0), 2, 27),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 28),
                Token(TokenType.LET, TokenValue.StringValue("let"), 3, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("difference"), 3, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 3, 15),
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("number"), 3, 17),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 3, 24),
                Token(TokenType.NUMBER, TokenValue.NumberValue(10.0), 3, 26),
                Token(TokenType.SUBTRACT, TokenValue.StringValue("-"), 3, 29),
                Token(TokenType.NUMBER, TokenValue.NumberValue(4.0), 3, 31),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 32),
                Token(TokenType.LET, TokenValue.StringValue("let"), 4, 1),
                Token(TokenType.IDENTIFIER, TokenValue.StringValue("quotient"), 4, 5),
                Token(TokenType.COLON, TokenValue.StringValue(":"), 4, 13),
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("number"), 4, 15),
                Token(TokenType.ASSIGN, TokenValue.StringValue("="), 4, 22),
                Token(TokenType.NUMBER, TokenValue.NumberValue(8.0), 4, 24),
                Token(TokenType.DIVIDE, TokenValue.StringValue("/"), 4, 26),
                Token(TokenType.NUMBER, TokenValue.NumberValue(2.0), 4, 28),
                Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 4, 29),
            )
        assertEquals(expectedTokens, actualTokens, "Tokens do not match")
    }
}

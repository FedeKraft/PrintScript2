import handlers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class LexerTest3 {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test full code with types and operators`() {
        val code = readSourceCodeFromFile("AllKnownTokens.txt")
        val lexer = Lexer(code, listOf(
            WhitespaceHandler(),
            IdentifierOrKeywordHandler(),
            StringLiteralHandler(),
            AssignationHandler(),
            SemicolonHandler(),
            NumberHandler(),
            ParenthesisHandler(),
            ColonAndTypeHandler(),
            ArithmeticOperatorHandler(),
        ))
        val tokens = lexer.tokenize()

        // Imprimir los tokens generados por el lexer
        println("Tokens generados por el lexer:")
        tokens.forEach { println(it) }

        val expectedTokens = listOf(
            Token(TokenType.LET, TokenValue.StringValue("let"), 1, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("name"), 1, 5),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 1, 9),
            Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), 1, 11),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 1, 18),
            Token(TokenType.STRING, TokenValue.StringValue("Olive"), 1, 20),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 1, 27),
            Token(TokenType.LET, TokenValue.StringValue("let"), 2, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("age"), 2, 5),
            Token(TokenType.COLON, TokenValue.StringValue(":"), 2, 8),
            Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), 2, 10),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 2, 17),
            Token(TokenType.NUMBER, TokenValue.NumberValue(30.0), 2, 19),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 2, 21),
            Token(TokenType.LET, TokenValue.StringValue("let"), 3, 1),
            Token(TokenType.IDENTIFIER, TokenValue.StringValue("result"), 3, 5),
            Token(TokenType.ASSIGN, TokenValue.StringValue("="), 3, 12),
            Token(TokenType.NUMBER, TokenValue.NumberValue(5.0), 3, 14),
            Token(TokenType.SUM, TokenValue.StringValue("+"), 3, 16),
            Token(TokenType.NUMBER, TokenValue.NumberValue(10.0), 3, 18),
            Token(TokenType.SUBTRACT, TokenValue.StringValue("-"), 3, 21),
            Token(TokenType.NUMBER, TokenValue.NumberValue(3.0), 3, 23),
            Token(TokenType.MULTIPLY, TokenValue.StringValue("*"), 3, 25),
            Token(TokenType.NUMBER, TokenValue.NumberValue(2.0), 3, 27),
            Token(TokenType.DIVIDE, TokenValue.StringValue("/"), 3, 29),
            Token(TokenType.NUMBER, TokenValue.NumberValue(1.0), 3, 31),
            Token(TokenType.SEMICOLON, TokenValue.StringValue(";"), 3, 32),
        )

        assertEquals(expectedTokens.size, tokens.size, "Token count mismatch")

        for ((i, expectedToken) in expectedTokens.withIndex()) {
            val actualToken = tokens[i]
            assertEquals(expectedToken.type, actualToken.type, "Token type mismatch at index $i")
            assertEquals(expectedToken.value, actualToken.value, "Token value mismatch at index $i")
            assertEquals(expectedToken.line, actualToken.line, "Token line mismatch at index $i")
            assertEquals(expectedToken.column, actualToken.column, "Token column mismatch at index $i")
        }
    }
}

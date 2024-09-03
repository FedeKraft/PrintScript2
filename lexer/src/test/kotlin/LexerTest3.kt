import lexer.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import token.Token
import token.TokenType
import java.io.File

class LexerTest3 {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test full code with types and operators`() {
        val code = readSourceCodeFromFile("AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(code)
        val actualTokens = mutableListOf<Token>()

        while (lexer.hasNextToken()) {
            val token = lexer.nextToken()
            actualTokens.add(token)
        }
        println(actualTokens)
    }

    @Test
    fun `test lexer partial lines`() {
        val code = readSourceCodeFromFile("AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(code)

        // Function to lex a specified number of lines
        fun lexLines(numberOfLines: Int): List<Token> {
            val tokens = mutableListOf<Token>()
            var linesLexed = 0

            while (lexer.hasNextToken() && linesLexed < numberOfLines) {
                val token = lexer.nextToken()
                tokens.add(token)
                if (token.type == TokenType.SEMICOLON) {
                    linesLexed++
                }
            }
            return tokens
        }

        // Lex the first 2 lines
        val tokensFirstTwoLines = lexLines(3)

        println("Tokens from the first three lines: $tokensFirstTwoLines")
        assertEquals(19, tokensFirstTwoLines.size) // Adjust the expected number according to your expectations
    }

    @Test
    fun `test lexer with no errors`() {
        val code = readSourceCodeFromFile("AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(code)

        val tokensBeforeError = mutableListOf<Token>()
        var errorEncountered = false

        while (lexer.hasNextToken() && !errorEncountered) {
            val token = lexer.nextToken()
            tokensBeforeError.add(token)

            if (token.type == TokenType.UNKNOWN) {
                errorEncountered = true
                println("Error encountered: $token")
            }
        }

        assertFalse(errorEncountered, "Expected an error token but none was found")
        println("Tokens before error: $tokensBeforeError")
    }

    @Test
    fun `test lexer stops at errors`() {
        val code = readSourceCodeFromFile("StatementsWithErrors.txt")
        val lexer = LexerFactory().createLexer1_0(code)

        val tokensBeforeError = mutableListOf<Token>()
        var errorEncountered = false

        while (lexer.hasNextToken() && !errorEncountered) {
            val token = lexer.nextToken()
            tokensBeforeError.add(token)

            if (token.type == TokenType.UNKNOWN) {
                errorEncountered = true
                println("Error encountered: $token")
            }
        }

        assertTrue(errorEncountered, "Expected an error token but none was found")
        println("Tokens before error: $tokensBeforeError")
    }
}

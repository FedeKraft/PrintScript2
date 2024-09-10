import factory.LexerFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import reader.Reader
import token.Token
import token.TokenType
import java.io.File

class LexerTest3 {

    @Test
    fun `testing new  lexer`(){
        val reader = Reader("src/test/resources/AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()
        while (lexer.hasNextToken()) {
            val token = lexer.nextToken()
            actualTokens.add(token)
        }
        println(actualTokens)
    }

    @Test
    fun `test full code with types and operators`() {
        val reader = Reader("src/test/resources/AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(reader)
        val actualTokens = mutableListOf<Token>()
        while (lexer.hasNextToken()) {
            val token = lexer.nextToken()
            actualTokens.add(token)
            println(token)
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
        println("Total tokens lexed: ${tokensFirstTwoLines.size}")  // Imprime el total de tokens

        assertEquals(19, tokensFirstTwoLines.size) // Ajusta el número esperado según tu archivo
    }

    @Test
    fun `test lexer with no errors`() {
        val reader = Reader("src/test/resources/StatementsWithErrors.txt")
        val lexer = LexerFactory().createLexer1_0(reader)

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

        assertFalse(errorEncountered)
        println("Tokens before error: $tokensBeforeError")
    }

    @Test
    fun `test lexer stops at errors`() {
        val reader = Reader("src/test/resources/StatementsWithErrors.txt")
        val lexer = LexerFactory().createLexer1_0(reader)

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


    @Test
    fun `testeando el reader` (){

        val filePath = "src/test/resources/AllKnownTokens.txt"
        val reader = Reader(filePath)
        while (reader.hasNextChar()){
            println(reader.read())
        }


    }
}

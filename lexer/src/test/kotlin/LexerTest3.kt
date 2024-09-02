import lexer.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Token
import token.TokenType
import token.TokenValue
import java.io.File

class LexerTest3 {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test full code with types and operators`() {
        val code = readSourceCodeFromFile("AllKnownTokens.txt")
        val lexer = LexerFactory().createLexer1_0(code)
        val currentStatementTokens = mutableListOf<Token>() // Lista temporal para almacenar tokens de la sentencia actual

        while (lexer.hasNextToken()) {
            val token = lexer.nextToken()
            currentStatementTokens.add(token)

            if (token.type == TokenType.SEMICOLON) {
                // Imprime los tokens de la sentencia actual
                println(currentStatementTokens)
                // Limpia la lista para la próxima sentencia
                currentStatementTokens.clear()
            }
        }

        // Imprime cualquier token restante que no haya terminado con un ';'
        if (currentStatementTokens.isNotEmpty()) {
            println(currentStatementTokens)
        }
    }

}

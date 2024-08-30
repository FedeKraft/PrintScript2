package lexer

import token.Token
import token.TokenHandler
import token.TokenProvider
import token.TokenType
import token.TokenValue

class Lexer(val code: String, private val handlers: List<TokenHandler>) : TokenProvider {
    var position = 0
    var line = 1
    var column = 1

    // Método para obtener el siguiente token en la secuencia.
    private fun nextToken(): Token? {
        while (position < code.length) {
            val currentChar = code[position]
            val matched = false

            for (handler in handlers) {
                val token = handler.handle(currentChar, this)
                if (token != null) {
                    // Actualizar posición y columnas según sea necesario.
                    position += token.value.toString().length
                    column += token.value.toString().length
                    return token
                }
            }

            if (!matched && !currentChar.isWhitespace()) {
                val unknownToken = Token(
                    TokenType.UNKNOWN,
                    TokenValue.StringValue(currentChar.toString()),
                    line,
                    column,
                )
                throw IllegalArgumentException("Unknown token found: $unknownToken")
            }

            // Si el carácter actual es un espacio en blanco, simplemente avanza la posición.
            position++
            column++
        }

        // Retorna null cuando no hay más tokens.
        return null
    }

    override fun getNextStatementTokens(): List<Token>? {
        val statementTokens = mutableListOf<Token>()
        while (true) {
            val token = nextToken() ?: break
            statementTokens.add(token)
            if (token.type == TokenType.SEMICOLON) {
                break
            }
        }
        return if (statementTokens.isEmpty()) null else statementTokens
    }
}

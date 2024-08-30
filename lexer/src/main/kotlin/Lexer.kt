import handlers.*

class Lexer(val code: String, private val handlers: List<TokenHandler>) {
    private var position = 0
    private var line = 1
    private var column = 1

    // Método para obtener el siguiente token en la secuencia.
    private fun nextToken(): Token? {
        while (position < code.length) {
            val currentChar = code[position]
            var matched = false

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
                    column
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

    // Secuencia que produce tokens de manera incremental.
    fun tokenize(): Sequence<Token> = sequence {
        while (true) {
            val token = nextToken() ?: break
            yield(token)
        }
    }

    // Secuencia que produce listas de tokens, una por cada statement.
    fun tokenizeByStatement(): Sequence<List<Token>> = sequence {
        val statementTokens = mutableListOf<Token>()

        for (token in tokenize()) {
            statementTokens.add(token)

            // Emitimos los tokens acumulados como un statement al encontrar un punto y coma.
            if (token.type == TokenType.SEMICOLON) {
                yield(statementTokens.toList())  // Emitimos una copia inmutable de los tokens
                statementTokens.clear()  // Limpiamos la lista para el próximo statement
            }
        }

        // Emitir los tokens restantes si no hay punto y coma final.
        if (statementTokens.isNotEmpty()) yield(statementTokens.toList())
    }
}

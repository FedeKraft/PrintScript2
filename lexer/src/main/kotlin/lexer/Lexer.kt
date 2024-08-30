package lexer

import token.Token
import token.TokenType
import token.TokenValue
import token.TokenPatterns  // Importar el objeto TokenPatterns

class Lexer(val code: String) {
    var position = 0
    var line = 1
    var column = 1

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        while (true) {
            val token = nextToken() ?: break
            tokens.add(token)
        }
        return tokens
    }

    // Método para obtener el siguiente token en la secuencia.
    private fun nextToken(): Token? {
        if (position >= code.length) return null

        val remainingCode = code.substring(position)

        // Manejo de nueva línea
        if (remainingCode.startsWith("\n")) {
            line++
            column = 1 // Reiniciar la columna al comenzar una nueva línea
            position++
            return nextToken() // Continuar con el siguiente token
        }

        // Match y creación de tokens basado en patrones
        return when {
            TokenPatterns.KEYWORDS.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.KEYWORDS, TokenType.LET, TokenType.PRINT)
            }
            TokenPatterns.STRING_TYPE.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.STRING_TYPE, TokenType.STRING_TYPE)
            }
            TokenPatterns.NUMBER_TYPE.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.NUMBER_TYPE, TokenType.NUMBER_TYPE)
            }
            TokenPatterns.IDENTIFIER.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.IDENTIFIER, TokenType.IDENTIFIER)
            }
            TokenPatterns.NUMBER.find(remainingCode)?.range?.first == 0 -> {
                val match = TokenPatterns.NUMBER.find(remainingCode)!!
                val tokenValue = match.value.toDouble() // Convertir a Double
                val token = Token(TokenType.NUMBER, TokenValue.NumberValue(tokenValue), line, column)
                position += match.value.length
                column += match.value.length
                return token
            }
            TokenPatterns.STRING.find(remainingCode)?.range?.first == 0 -> {
                val match = TokenPatterns.STRING.find(remainingCode)!!
                val tokenValue = match.value.trim('"', '\'')  // Quita las comillas del literal String
                val token = Token(TokenType.STRING, TokenValue.StringValue(tokenValue), line, column)
                position += match.value.length
                column += match.value.length
                return token
            }
            TokenPatterns.OPERATOR.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.OPERATOR, TokenType.ARITHMETIC_OP)
            }
            TokenPatterns.SYMBOL.find(remainingCode)?.range?.first == 0 -> {
                matchToken(TokenPatterns.SYMBOL, TokenType.SEMICOLON, TokenType.COLON, TokenType.LEFT_PARENTHESIS, TokenType.RIGHT_PARENTHESIS)
            }
            TokenPatterns.WHITESPACE.find(remainingCode)?.range?.first == 0 -> {
                // Ignorar espacios en blanco
                val match = TokenPatterns.WHITESPACE.find(remainingCode)!!
                position += match.value.length
                column += match.value.length
                return nextToken() // Continuar con el siguiente token
            }
            else -> {
                // Token no reconocido
                val unknownToken = Token(
                    TokenType.UNKNOWN,
                    TokenValue.StringValue(remainingCode[0].toString()),
                    line,
                    column
                )
                throw IllegalArgumentException("Unknown token found: $unknownToken")
            }
        }
    }

    private fun matchToken(pattern: Regex, vararg types: TokenType): Token {
        val match = pattern.find(code.substring(position))!!
        val tokenType = when (match.value) {
            "let" -> TokenType.LET
            "println" -> TokenType.PRINT
            "+" -> TokenType.SUM
            "-" -> TokenType.SUBTRACT
            "*" -> TokenType.MULTIPLY
            "/" -> TokenType.DIVIDE
            "=" -> TokenType.ASSIGN
            ";" -> TokenType.SEMICOLON
            ":" -> TokenType.COLON
            "(" -> TokenType.LEFT_PARENTHESIS
            ")" -> TokenType.RIGHT_PARENTHESIS
            "String" -> TokenType.STRING_TYPE // Tipo específico para String
            "Number" -> TokenType.NUMBER_TYPE // Tipo específico para Number
            else -> types.first()
        }
        val token = Token(tokenType, TokenValue.StringValue(match.value), line, column)
        position += match.value.length
        column += match.value.length // Incrementar la columna por la longitud del token
        return token
    }

    // Método existente para obtener tokens de la siguiente declaración
    fun getNextStatementTokens(): List<Token>? {
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

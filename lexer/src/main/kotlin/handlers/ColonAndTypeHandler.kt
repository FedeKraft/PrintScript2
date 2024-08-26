
package handlers

import Lexer
import Token
import TokenHandler
import TokenType
import TokenValue

class ColonAndTypeHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        return when {
            currentChar == ':' -> {
                lexer.position++
                lexer.column++
                Token(TokenType.COLON, TokenValue.StringValue(":"), lexer.line, lexer.column - 1)
            }
            lexer.code.startsWith("String", lexer.position) -> {
                lexer.position += 6
                lexer.column += 6
                Token(TokenType.STRING_TYPE, TokenValue.StringValue("String"), lexer.line, lexer.column - 6)
            }
            lexer.code.startsWith("Number", lexer.position) -> {
                lexer.position += 6
                lexer.column += 6
                Token(TokenType.NUMBER_TYPE, TokenValue.StringValue("Number"), lexer.line, lexer.column - 6)
            }
            else -> null
        }
    }
}

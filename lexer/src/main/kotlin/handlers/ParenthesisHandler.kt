
package handlers

import Lexer
import Token
import TokenHandler
import TokenType
import TokenValue

class ParenthesisHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        return when (currentChar) {
            '(' -> {
                lexer.position++
                lexer.column++
                Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), lexer.line, lexer.column - 1)
            }
            ')' -> {
                lexer.position++
                lexer.column++
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), lexer.line, lexer.column - 1)
            }
            else -> null
        }
    }
}

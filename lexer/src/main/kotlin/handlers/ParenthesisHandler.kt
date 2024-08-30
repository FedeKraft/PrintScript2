
package handlers

import lexer.Lexer
import token.Token
import token.TokenHandler
import token.TokenType
import token.TokenValue

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

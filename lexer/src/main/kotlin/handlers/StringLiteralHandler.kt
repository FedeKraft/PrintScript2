
package handlers

import Lexer
import Token
import TokenHandler
import TokenType
import TokenValue

class StringLiteralHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        if (currentChar == '"') {
            val start = lexer.position
            lexer.position++
            lexer.column++
            while (lexer.position < lexer.code.length && lexer.code[lexer.position] != '"') {
                lexer.position++
                lexer.column++
            }
            lexer.position++
            lexer.column++
            val value = lexer.code.substring(start + 1, lexer.position - 1)

            return Token(TokenType.STRING, TokenValue.StringValue(value), lexer.line, lexer.column - value.length - 2)
        }
        return null
    }
}

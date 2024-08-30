
package handlers

import lexer.Lexer
import token.Token
import token.TokenHandler
import token.TokenType
import token.TokenValue

class AssignationHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        if (currentChar == '=') {
            lexer.position++
            lexer.column++
            return Token(TokenType.ASSIGN, TokenValue.StringValue("="), lexer.line, lexer.column - 1)
        }
        return null
    }
}

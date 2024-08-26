package handlers

import Lexer
import Token
import TokenHandler

class WhitespaceHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        if (currentChar.isWhitespace()) {
            if (currentChar == '\n') {
                lexer.line++
                lexer.column = 1
            } else {
                lexer.column++
            }
            lexer.position++
            return null
        }
        return null
    }
}

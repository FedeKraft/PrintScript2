package token

import lexer.Lexer

interface TokenHandler {
    fun handle(currentChar: Char, lexer: Lexer): Token?
}

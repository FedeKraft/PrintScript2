
package handlers

import lexer.Lexer
import token.Token
import token.TokenHandler
import token.TokenType
import token.TokenValue

class IdentifierOrKeywordHandler : TokenHandler {
    override fun handle(currentChar: Char, lexer: Lexer): Token? {
        if (currentChar.isLetter()) {
            val start = lexer.position
            while (lexer.position < lexer.code.length && lexer.code[lexer.position].isLetterOrDigit()) {
                lexer.position++
                lexer.column++
            }
            val word = lexer.code.substring(start, lexer.position)
            val type = when (word) {
                "let" -> TokenType.LET
                "print" -> TokenType.PRINT
                else -> TokenType.IDENTIFIER
            }
            return Token(type, TokenValue.StringValue(word), lexer.line, lexer.column - word.length)
        }
        return null
    }
}

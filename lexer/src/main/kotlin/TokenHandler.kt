

interface TokenHandler {
    fun handle(currentChar: Char, lexer: Lexer): Token?
}

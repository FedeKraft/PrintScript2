package factory

import lexer.TokenProvider
import parser.ParserDirector

class ParserFactory {
    // Parser para la versión 1.0
    fun createParser1_0(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(tokenProvider)

    // Parser para la versión 1.1 con nuevas funcionalidades
    fun createParser1_1(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(tokenProvider)
}

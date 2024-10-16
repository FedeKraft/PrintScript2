package factory

import lexer.TokenProvider
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.ConstDeclarationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import token.TokenType

class ParserFactory {
    // Parser para la versión 1.0
    fun createParser1_0(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(
            tokenProvider,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

    // Parser para la versión 1.1 con nuevas funcionalidades
    fun createParser1_1(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(
            tokenProvider,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
                TokenType.CONST to ConstDeclarationParser(),
            ),
        )
}

package parser

import parserTypes.AssignationParser
import parserTypes.ConstDeclarationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import token.TokenProvider
import token.TokenType

class ParserFactory {
    // Parser para la versión 1.0
    fun createParser1_0(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(
            tokenProvider,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.PRINT to PrintParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

    // Parser para la versión 1.1 con nuevas funcionalidades
    fun createParser1_1(tokenProvider: TokenProvider): ParserDirector =
        ParserDirector(
            tokenProvider,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.CONST to ConstDeclarationParser(), // Comando para const
                TokenType.PRINT to PrintParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )
}

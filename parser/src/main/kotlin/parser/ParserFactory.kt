package parser

import command.AssignationCommand
import command.PrintStatementCommand
import command.VariableDeclarationStatementCommand
import org.example.parser.Parser
import token.TokenProvider
import token.TokenType

class ParserFactory {

    fun createParser1_0(tokenProvider: TokenProvider): Parser {
        return Parser(
            tokenProvider,
            mapOf(
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            ),
        )
    }

    fun createParser1_1(tokenProvider: TokenProvider): Parser {
        return Parser(
            tokenProvider,
            mapOf(
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
                // ....
            ),
        )
    }
}

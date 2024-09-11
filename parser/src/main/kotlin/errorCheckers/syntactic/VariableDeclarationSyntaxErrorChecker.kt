package errorCheckers.syntactic

import org.example.errorCheckers.ErrorChecker
import token.Token
import token.TokenType

class VariableDeclarationSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        val necessaryTokens =
            mutableListOf<TokenType>(TokenType.LET, TokenType.IDENTIFIER, TokenType.COLON, TokenType.ASSIGN)
        if (tokens.size < 6 && !necessaryTokens.contains(TokenType.LET)) {
            throw RuntimeException("Missing LET token in line: ${tokens.last().line}, column: ${tokens.last().column}")
        }
        if (tokens.size < 6 && !necessaryTokens.contains(TokenType.COLON)) {
            throw RuntimeException(
                "Missing COLON token after variable in line: ${tokens.last().line}, column: ${tokens[2].column}",
            )
        }
        if (tokens.size < 6 && !necessaryTokens.contains(TokenType.ASSIGN)) {
            throw RuntimeException(
                "Missing ASSIGN token in line: ${tokens.last().line}, column: ${tokens.last().column - 1}",
            )
        }

        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException(
                "Unknown token in variable declaration, line: ${unknownToken.line}, column: ${unknownToken.column}",
            )
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val iterator = tokens.iterator()
        var token = iterator.next()

        if (token.type != TokenType.LET) {
            throw RuntimeException("Expected 'LET', found ${token.type} line: ${token.line}, column: ${token.column}")
        }
        token = iterator.next()

        if (token.type != TokenType.IDENTIFIER) {
            throw RuntimeException(
                "Expected 'IDENTIFIER', found ${token.type} line: ${token.line}, column: ${token.column}",
            )
        }
        token = iterator.next()

        if (token.type != TokenType.COLON) {
            throw RuntimeException("Expected ':', found ${token.type} line: ${token.line}, column: ${token.column}")
        }
        token = iterator.next()

        if (token.type != TokenType.STRING_TYPE && token.type != TokenType.NUMBER_TYPE) {
            throw RuntimeException(
                "Expected type 'STRING_TYPE' or 'NUMBER_TYPE', found " +
                    "${token.type} line: ${token.line}, column: ${token.column}",
            )
        }
        token = iterator.next()

        if (token.type != TokenType.ASSIGN) {
            throw RuntimeException("Expected '=', found ${token.type} line: ${token.line}, column: ${token.column}")
        }
        token = iterator.next()

        if (token.type != TokenType.STRING && token.type != TokenType.NUMBER && token.type != TokenType.IDENTIFIER) {
            throw RuntimeException(
                "Expected value token, found ${token.type} line: ${token.line}, column: ${token.column}",
            )
        }
    }
}

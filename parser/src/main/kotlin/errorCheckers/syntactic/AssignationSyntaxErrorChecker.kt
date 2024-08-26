package org.example.errorCheckers.syntactic

import Token
import TokenType
import org.example.errorCheckers.ErrorChecker

class AssignationSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        if (tokens.size < 3) {
            throw RuntimeException("Invalid number of tokens in variable assignment")
        }
        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException(
                "Unknown token in print statement, line: ${unknownToken.line}, column: ${unknownToken.column}",
            )
        }
        val tokenTypes = tokens.map { it.type }
        val requiredTokenTypes = listOf(TokenType.IDENTIFIER, TokenType.ASSIGN)

        for (tokenType in requiredTokenTypes) {
            if (tokenType !in tokenTypes) {
                throw RuntimeException("Missing token $tokenType in variable assignment")
            }
        }

        if (TokenType.NUMBER !in tokenTypes && TokenType.STRING !in tokenTypes && TokenType.IDENTIFIER !in tokenTypes) {
            throw RuntimeException("Missing value token in variable assignment")
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val expectedOrder = listOf(
            TokenType.IDENTIFIER,
            TokenType.ASSIGN,
        )

        var index = 0
        for (expectedTokenType in expectedOrder) {
            while (index < tokens.size && tokens[index].type != expectedTokenType) {
                index++
            }
            if (index >= tokens.size) {
                throw RuntimeException("Unexpected token order in variable assignment")
            }
            index++
        }

        val valueTokenType = tokens[index].type
        if (valueTokenType !in listOf(TokenType.STRING, TokenType.NUMBER, TokenType.IDENTIFIER)) {
            throw RuntimeException("Invalid value token in variable assignment")
        }
    }
}

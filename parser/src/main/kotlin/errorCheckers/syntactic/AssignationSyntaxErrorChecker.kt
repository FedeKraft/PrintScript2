package org.example.errorCheckers.syntactic

import org.example.errorCheckers.ErrorChecker
import token.Token
import token.TokenType

class AssignationSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        if (tokens.size < 3) {
            if (tokens[0].type == TokenType.IDENTIFIER && tokens[1].type == TokenType.ASSIGN) {
                throw RuntimeException("Missing value token after = in line: ${tokens[1].line}, column: ${tokens[1].column}")
            }
            if (tokens[0].type == TokenType.IDENTIFIER && tokens[1].type != TokenType.ASSIGN) {
                throw RuntimeException("Missing ASSIGN token line: ${tokens.last().line}, column: ${tokens.last().column}")
            }
        }
        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException("Unknown token in assignment, line: ${unknownToken.line}, column: ${unknownToken.column}")
        }
        val tokenTypes = tokens.map { it.type }
        val requiredTokenTypes = listOf(TokenType.IDENTIFIER, TokenType.ASSIGN)

        for (tokenType in requiredTokenTypes) {
            val token = tokens.find { it.type == tokenType }
            if (token == null) {
                throw RuntimeException("Missing $tokenType token in variable assignment line: ${tokens.last().line}, column: ${tokens.last().column}")
            }
        }

        if (TokenType.NUMBER !in tokenTypes && TokenType.STRING !in tokenTypes && TokenType.IDENTIFIER !in tokenTypes) {
            throw RuntimeException("Missing value token in variable assignment line: ${tokens.last().line}, column: ${tokens.last().column}")
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val expectedOrder = listOf(TokenType.IDENTIFIER, TokenType.ASSIGN)
        var index = 0
        for (expectedTokenType in expectedOrder) {
            while (index < tokens.size && tokens[index].type != expectedTokenType) {
                index++
            }
            if (index >= tokens.size) {
                throw RuntimeException("Unexpected token order in variable assignment (line: ${tokens.last().line}, column: ${tokens.last().column})")
            }
            index++
        }

        if (index >= tokens.size || tokens[index].type !in listOf(TokenType.STRING, TokenType.NUMBER, TokenType.IDENTIFIER)) {
            throw RuntimeException("Invalid value token in variable assignment line: ${tokens.last().line}, column: ${tokens.last().column}")
        }
    }
}

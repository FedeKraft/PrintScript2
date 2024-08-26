package org.example.errorCheckers.syntactic

import Token
import TokenType
import org.example.errorCheckers.ErrorChecker

class VariableDeclarationSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        if (tokens.size < 6) { throw RuntimeException("Invalid number of tokens in variable declaration") }
        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) { throw RuntimeException(
            "Unknown token in print statement, linea ${unknownToken.line}, columna ${unknownToken.column}",
        ) }
        val tokenTypes = tokens.map { it.type }
        val requiredTokenTypes = listOf(TokenType.LET, TokenType.IDENTIFIER, TokenType.COLON, TokenType.ASSIGN)
        for (tokenType in requiredTokenTypes) {
            if (tokenType !in tokenTypes) { throw RuntimeException("Missing token $tokenType in variable declaration") }
        }

        if (TokenType.NUMBER_TYPE !in tokenTypes && TokenType.STRING_TYPE !in tokenTypes) { throw RuntimeException(
            "Missing type token in variable declaration",
        ) }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val iterator = tokens.iterator()
        var token = iterator.next()

        // Comprobar 'LET'
        if (token.type != TokenType.LET) {
            throw RuntimeException("Expected 'LET', found ${token.type}")
        }
        token = iterator.next()

        // Comprobar IDENTIFIER
        if (token.type != TokenType.IDENTIFIER) {
            throw RuntimeException("Expected 'IDENTIFIER', found ${token.type}")
        }
        token = iterator.next()

        // Comprobar 'COLON'
        if (token.type != TokenType.COLON) {
            throw RuntimeException("Expected ':', found ${token.type}")
        }
        token = iterator.next()

        // Comprobar TYPE (STRING_TYPE o NUMBER_TYPE)
        if (token.type != TokenType.STRING_TYPE && token.type != TokenType.NUMBER_TYPE) {
            throw RuntimeException("Expected type 'STRING_TYPE' or 'NUMBER_TYPE', found ${token.type}")
        }
        token = iterator.next()

        // Comprobar 'ASSIGN'
        if (token.type != TokenType.ASSIGN) {
            throw RuntimeException("Expected '=', found ${token.type}")
        }
        token = iterator.next()

        // Comprobar VALUE (STRING, NUMBER o IDENTIFIER)
        if (token.type != TokenType.STRING && token.type != TokenType.NUMBER && token.type != TokenType.IDENTIFIER) {
            throw RuntimeException("Expected value token, found ${token.type}")
        }
    }
}

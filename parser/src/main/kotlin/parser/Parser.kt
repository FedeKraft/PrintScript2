package org.example.parser

import StatementNode
import command.ParseCommand
import token.TokenProvider
import token.TokenType

class Parser(private val tokenProvider: TokenProvider, private val commands: Map<TokenType, ParseCommand>) {

    fun nextStatement(): StatementNode? {
        val tokens = tokenProvider.getNextStatementTokens() ?: return null
        if (tokens.isEmpty()) return null

        val firstToken = tokens.first()
        val command = commands[firstToken.type]
        if (command != null) {
            return command.execute(tokens) as StatementNode
        } else {
            throw RuntimeException(
                "Token inesperado en línea ${firstToken.line}, columna ${firstToken.column}: ${firstToken.type}",
            )
        }
    }
}

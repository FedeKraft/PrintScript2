package org.example.parser

import StatementNode
import command.ParseCommand
import token.TokenProvider
import token.TokenType

class Parser(private val tokenProvider: TokenProvider, private val commands: Map<TokenType, ParseCommand>): ASTProvider {

    fun nextStatement(): StatementNode {
        val tokens = mutableListOf<token.Token>()
        while (tokenProvider.hasNextToken()) {
            val token = tokenProvider.getNextToken()
            if (token.type == TokenType.SEMICOLON) {
                break
            }
            if (token.type == TokenType.UNKNOWN) {
                throw RuntimeException("Unknown token in variable assignment at line: ${token.line}, column: ${token.column}")
            }
            tokens.add(token)
        }
        if (tokens.isEmpty()) {
            throw NoSuchElementException("No more tokens available")
        }
        val command = commands[tokens[0].type]
        if (command != null) {
            return command.execute(tokens)
        }
        throw RuntimeException("Syntax error, cannot initialize a statement with token: ${tokens[0].value}, line: ${tokens[0].line}, column: ${tokens[0].column}")
    }

    override fun getNextAST(): StatementNode {
        return nextStatement()
    }

    override fun hasNextAST(): Boolean {
        return tokenProvider.hasNextToken()
    }
}
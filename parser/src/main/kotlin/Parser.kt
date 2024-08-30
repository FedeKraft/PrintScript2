package org.example

import Lexer
import StatementNode
import TokenType
import command.ParseCommand
class Parser(private val lexer: Lexer, private val commands : Map<TokenType, ParseCommand>) {
    // Este método produce un statement a la vez.
    fun nextStatement(): StatementNode? {
        // Obtén el siguiente conjunto de tokens que representan un statement.
        val statementTokens = lexer.tokenizeByStatement().iterator()

        if (!statementTokens.hasNext()) {
            return null  // No hay más statements.
        }

        val tokens = statementTokens.next()
        if (tokens.isEmpty()) return null  // No hay tokens para procesar.

        val firstToken = tokens.first()
        val command = commands[firstToken.type]
        if (command != null) {
            return command.execute(tokens) as StatementNode
        } else {
            throw RuntimeException(
                "Token inesperado en línea ${firstToken.line}, columna ${firstToken.column}: ${firstToken.type}"
            )
        }
    }
}

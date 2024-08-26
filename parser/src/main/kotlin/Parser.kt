package org.example

import PrintStatementCommand
import ProgramNode
import StatementNode
import Token
import TokenType
import command.VariableDeclarationStatementCommand
import org.example.command.AssignationCommand

class Parser {

    private val commands = mapOf(
        TokenType.LET to VariableDeclarationStatementCommand(),
        TokenType.PRINT to PrintStatementCommand(),
        TokenType.IDENTIFIER to AssignationCommand(),
    )

    fun parse(tokens: List<Token>): ProgramNode {
        val statements = mutableListOf<StatementNode>()
        var index = 0
        while (index < tokens.size) {
            val currentStatement = getStatement(tokens, index)
            val statementTokens = currentStatement.first
            val token = tokens[index]
            val command = commands[token.type]
            if (command != null) {
                val node = command.execute(statementTokens)
                statements.add(node as StatementNode)
                index = currentStatement.second
            } else {
                throw RuntimeException(
                    "Token inesperado en linea ${token.line}, columna ${token.column}: ${token.type}",
                )
            }
        }
        return ProgramNode(statements)
    }

    private fun getStatement(tokens: List<Token>, index: Int): Pair<List<Token>, Int> {
        val statementTokens = mutableListOf<Token>()
        var currentIndex = index
        while (currentIndex < tokens.size) {
            val token = tokens[currentIndex]
            if (token.type == TokenType.SEMICOLON) {
                return Pair(statementTokens, currentIndex + 1)
            }
            statementTokens.add(token)
            currentIndex++
        }
        throw RuntimeException("No se encontró un punto y coma al final de la línea")
    }
}

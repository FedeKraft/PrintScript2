package org.example.parser

import StatementNode

interface ASTProvider {
    fun getNextAST(): StatementNode
    fun hasNextAST(): Boolean
}

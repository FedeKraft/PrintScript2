package parser

import ast.StatementNode

interface ASTProvider {
    fun getNextAST(): StatementNode

    fun hasNextAST(): Boolean
}

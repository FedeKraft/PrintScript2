package parser

import ast.ConstDeclarationNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StatementNode
import token.TokenType
import java.util.Stack

class DummyASTProvider : ASTProvider {
    private val nodes = Stack<StatementNode>()

    init {
        // Primero, declara la constante PI
        nodes.push(
            PrintStatementNode(IdentifierNode("PI", 0, 0), 0, 0),
        )

        // Luego, imprime el valor de la constante PI
        nodes.push(
            ConstDeclarationNode(
                identifier = IdentifierNode("PI", 0, 0),
                type = TokenType.NUMBER,
                value = NumberLiteralNode(3.1416, 0, 0),
                0,
                0,
            ),
        )
    }

    override fun getNextAST(): StatementNode = nodes.pop()

    override fun hasNextAST(): Boolean = nodes.isNotEmpty()
}

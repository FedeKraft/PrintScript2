package parser

import ast.BlockNode
import ast.BooleanLiteralNode
import ast.IfElseNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import java.util.Stack

class DummyASTProvider : ASTProvider {

    private val astNodes = Stack<StatementNode>()
    init {

        astNodes.push(
            IfElseNode(
                BooleanLiteralNode(true),
                BlockNode(
                    listOf(
                        PrintStatementNode(
                            StringLiteralNode("Hola, mundo!"),
                        ),
                    ),
                ),
                null,
            ),
        )
        astNodes.push(
            IfElseNode(
                condition = BooleanLiteralNode(false),
                ifBlock = BlockNode(
                    statements = listOf(
                        PrintStatementNode(
                            expression = StringLiteralNode("Este mensaje no se imprimirá"),
                        ),
                    ),
                ),
                elseBlock = BlockNode(
                    statements = listOf(
                        PrintStatementNode(
                            expression =
                            StringLiteralNode("Este mensaje se imprimirá"),
                        ),
                    ),
                ),
            ),
        )
    }

    override fun getNextAST(): StatementNode {
        return astNodes.pop()
    }

    override fun hasNextAST(): Boolean {
        return astNodes.isNotEmpty()
    }
}

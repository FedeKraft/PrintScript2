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
                BooleanLiteralNode(true, 5, 8),
                BlockNode(
                    listOf(
                        PrintStatementNode(
                            StringLiteralNode("Hola, mundo!", 6, 10),
                            6,
                            9,
                        ),
                    ),
                    6,
                    8,
                ),
                null,
                5,
                8,
            ),
        )
        astNodes.push(
            IfElseNode(
                condition = BooleanLiteralNode(true, 10, 12),
                ifBlock = BlockNode(
                    statements = listOf(
                        PrintStatementNode(
                            expression = StringLiteralNode("Este mensaje no se imprimirá", 11, 14),
                            11,
                            13,
                        ),
                    ),
                    11,
                    12,
                ),
                elseBlock = BlockNode(
                    statements = listOf(
                        PrintStatementNode(
                            expression = StringLiteralNode("Este mensaje se imprimirá", 12, 15),
                            12,
                            14,
                        ),
                    ),
                    12,
                    13,
                ),
                10,
                12,
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

package ast

import token.TokenType

sealed class StatementNode

data class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val type: TokenType,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode()

data class AssignationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode()

data class PrintStatementNode(
    val expression: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode()

sealed class ExpressionNode

data class IdentifierNode(
    val name: String,
    val line: Int,
    val column: Int,
) : ExpressionNode()

data class NumberLiteralNode(
    val value: Double,
    val line: Int,
    val column: Int,
) : ExpressionNode()

data class StringLiteralNode(
    val value: String,
    val line: Int,
    val column: Int,
) : ExpressionNode()

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
    val line: Int,
    val column: Int,
) : ExpressionNode()

data class ConstDeclarationNode(
    val identifier: IdentifierNode,
    val type: TokenType,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode()

data class IfElseNode(
    val condition: ExpressionNode,
    val ifBlock: BlockNode,
    val elseBlock: BlockNode? = null,
    val line: Int,
    val column: Int,
) : StatementNode()

data class BlockNode(
    val statements: List<StatementNode>,
    val line: Int,
    val column: Int,
) : StatementNode()

data class BooleanLiteralNode(
    val value: Boolean,
    val line: Int,
    val column: Int,
) : ExpressionNode()

data class ReadInputNode(
    val value: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toString(): String = "ReadInputNode(value=$value, line=$line, column=$column)"
}

class ReadEnvNode(
    val value: Any,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toString(): String = "ReadEnvNode(value=$value, line=$line, column=$column)"
}

class NullValueNode(
    val defaultValue: Any,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toString(): String = "NullValueNode(line=$line, column=$column)"
}

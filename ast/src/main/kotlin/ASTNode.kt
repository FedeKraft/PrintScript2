
// Other node types that are part of the AST
sealed class StatementNode

data class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int, val column: Int)
    : StatementNode()

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


data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
    val line: Int,
    val column: Int,
) : ExpressionNode()

sealed class ExpressionNode

data class IdentifierNode(val name: String, val line: Int, val column: Int) : ExpressionNode()
data class NumberLiteralNode(val value: Double, val line: Int, val column: Int) : ExpressionNode()
data class StringLiteralNode(val value: String, val line: Int, val column: Int) : ExpressionNode()

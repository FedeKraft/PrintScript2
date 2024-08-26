import org.example.visitor.ASTVisitor

interface ASTNode {
    fun accept(visitor: ASTVisitor)
}

// ProgramNode represents the entire program, containing a list of statements
data class ProgramNode(val statements: List<StatementNode>) : ASTNode {
    override fun accept(visitor: ASTVisitor) {
        for (statement in statements) {
            statement.accept(visitor)
        }
    }
}

// Other node types that are part of the AST
sealed class StatementNode : ASTNode

data class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

data class AssignationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

data class PrintStatementNode(
    val expression: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

sealed class ExpressionNode : ASTNode
data class IdentifierNode(
    val name: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

data class StringLiteralNode(
    val value: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}
data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

data class NumberLiteralNode(val value: Double, val line: Int, val column: Int) : ExpressionNode() {
    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

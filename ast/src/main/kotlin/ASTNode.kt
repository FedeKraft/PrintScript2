import token.TokenType

sealed class StatementNode {
    abstract fun toFormattedString(variableTypes: MutableMap<String, Any>): String
}

data class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int, val column: Int
) : StatementNode() {

    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        val type = inferType(value, variableTypes)
        variableTypes[identifier.name] = type  // Almacenar el tipo de la variable
        return "let ${identifier.toFormattedString(variableTypes)}: $type = ${value.toFormattedString(variableTypes)};"
    }

    private fun inferType(expression: ExpressionNode, variableTypes: Map<String, Any>): Any {
        return when (expression) {
            is StringLiteralNode -> "String"
            is NumberLiteralNode -> "Number"
            is IdentifierNode -> variableTypes[expression.name] ?: "UnknownType"
            is BinaryExpressionNode -> {
                val leftType = inferType(expression.left, variableTypes)
                val rightType = inferType(expression.right, variableTypes)
                if (leftType == rightType) {
                    leftType
                } else {
                    "UnknownType"
                }
            }
            else -> "UnknownType"
        }
    }
}

data class AssignationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        return "${identifier.toFormattedString(variableTypes)}: ${variableTypes[identifier.name] ?: "UnknownType"} = ${value.toFormattedString(variableTypes)};"
    }
}

data class PrintStatementNode(
    val expression: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        return "println(${expression.toFormattedString(variableTypes)});"
    }
}

sealed class ExpressionNode {
    abstract fun toFormattedString(variableTypes: Map<String, Any>): String
}

data class IdentifierNode(val name: String, val line: Int, val column: Int) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return name
    }
}

data class NumberLiteralNode(val value: Double, val line: Int, val column: Int) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return value.toString()
    }
}

data class StringLiteralNode(val value: String, val line: Int, val column: Int) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return value
    }
}

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        val operatorSymbol = when (operator) {
            TokenType.SUM -> "+"
            TokenType.SUBTRACT -> "-"
            TokenType.MULTIPLY -> "*"
            TokenType.DIVIDE -> "/"
            else -> operator.toString()
        }
        return "${left.toFormattedString(variableTypes)} $operatorSymbol ${right.toFormattedString(variableTypes)}"
    }
}

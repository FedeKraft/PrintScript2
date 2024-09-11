package ast

import token.Token
import token.TokenType

sealed class StatementNode {
    abstract fun toFormattedString(variableTypes: MutableMap<String, Any>): String
}

data class VariableDeclarationNode(val identifier: IdentifierNode, val value: ExpressionNode) : StatementNode() {

    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        val type = inferType(value, variableTypes)
        variableTypes[identifier.name] = type // Almacenar el tipo de la variable
        return "let ${identifier.toFormattedString(variableTypes)}:" + " " +
            "$type = ${value.toFormattedString(variableTypes)};"
    }
    private fun inferType(expression: ExpressionNode, variableTypes: Map<String, Any>): Any {
        return when (expression) {
            is StringLiteralNode -> "String"
            is NumberLiteralNode -> "Number"
            is BooleanLiteralNode -> "Boolean"
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
) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        return "${identifier.toFormattedString(variableTypes)} = ${value.toFormattedString(variableTypes)};"
    }
}

data class PrintStatementNode(val expression: ExpressionNode) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        return "print(${expression.toFormattedString(variableTypes)});"
    } }

sealed class ExpressionNode {
    abstract fun toFormattedString(variableTypes: Map<String, Any>): String
}

data class IdentifierNode(val name: String) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return name
    }
}

data class NumberLiteralNode(val value: Double) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return value.toString()
    }
}

data class StringLiteralNode(val value: String) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return value
    }
}

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
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

data class ConstDeclarationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        val type = inferType(value, variableTypes)
        variableTypes[identifier.name] = type // Almacenar el tipo de la constante
        return "const ${identifier.toFormattedString(variableTypes)}: " +
            "$type = ${value.toFormattedString(variableTypes)};"
    }

    private fun inferType(expression: ExpressionNode, variableTypes: Map<String, Any>): Any {
        return when (expression) {
            is StringLiteralNode -> "String"
            is NumberLiteralNode -> "Number"
            is BooleanLiteralNode -> "Boolean"
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
        }
    }
}

data class IfElseNode(
    val condition: ExpressionNode,
    val ifBlock: BlockNode,
    val elseBlock: BlockNode? = null,
) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        val elseBlockString = if (elseBlock != null) {
            " else {\n${elseBlock.toFormattedString(variableTypes)}\n}"
        } else {
            ""
        }
        return "if (${condition.toFormattedString(variableTypes)})" +
            " {\n${ifBlock.toFormattedString(variableTypes)}\n}$elseBlockString"
    }
}

data class BlockNode(val statements: List<StatementNode>) : StatementNode() {
    override fun toFormattedString(variableTypes: MutableMap<String, Any>): String {
        return statements.joinToString(separator = "\n") { it.toFormattedString(variableTypes) }
    }
}

data class BooleanLiteralNode(
    val value: Boolean,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, Any>): String {
        return value.toString()
    }
}


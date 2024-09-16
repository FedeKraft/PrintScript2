package ast

import token.TokenType

sealed class StatementNode {
    abstract fun toFormattedString(variableTypes: Map<String, String>): String
}

data class VariableDeclarationNode(
    val identifier: IdentifierNode,
    val type: TokenType,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String {
        val inferredType = inferType(value, variableTypes)
        // Add the variable's type to the map
        (variableTypes as MutableMap)[identifier.name] = inferredType
        return "let ${identifier.toFormattedString(variableTypes)}:" +
            "$inferredType = ${value.toFormattedString(variableTypes)};"
    }

    private fun inferType(
        expression: ExpressionNode,
        variableTypes: Map<String, String>,
    ): String =
        when (expression) {
            is StringLiteralNode -> "string"
            is NumberLiteralNode -> "number"
            is BooleanLiteralNode -> "boolean"
            is IdentifierNode -> variableTypes[expression.name] ?: "UnknownType"
            is BinaryExpressionNode -> {
                val leftType = inferType(expression.left, variableTypes)
                val rightType = inferType(expression.right, variableTypes)
                if (leftType == rightType) leftType else "UnknownType"
            }
            else -> "UnknownType"
        }
}

data class AssignationNode(
    val identifier: IdentifierNode,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String =
        "${identifier.toFormattedString(variableTypes)} = ${value.toFormattedString(variableTypes)};"
}

data class PrintStatementNode(
    val expression: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(
        variableTypes: Map<String, String>,
    ): String = "println(${expression.toFormattedString(
        variableTypes,
    )});"
}

sealed class ExpressionNode {
    abstract fun toFormattedString(variableTypes: Map<String, String>): String
}

data class IdentifierNode(
    val name: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = name
}

data class NumberLiteralNode(
    val value: Double,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = value.toString()
}

data class StringLiteralNode(
    val value: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = "\"$value\""
}

data class BinaryExpressionNode(
    val left: ExpressionNode,
    val operator: TokenType,
    val right: ExpressionNode,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String {
        val operatorSymbol =
            when (operator) {
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
    val type: TokenType,
    val value: ExpressionNode,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String {
        val type = inferType(value, variableTypes)
        return "const ${identifier.toFormattedString(variableTypes)}:" +
            "$type = ${value.toFormattedString(variableTypes)};"
    }

    private fun inferType(
        expression: ExpressionNode,
        variableTypes: Map<String, String>,
    ): String =
        when (expression) {
            is StringLiteralNode -> "string"
            is NumberLiteralNode -> "number"
            is BooleanLiteralNode -> "boolean"
            is IdentifierNode -> variableTypes[expression.name] ?: "UnknownType"
            is BinaryExpressionNode -> {
                val leftType = inferType(expression.left, variableTypes)
                val rightType = inferType(expression.right, variableTypes)
                if (leftType == rightType) leftType else "UnknownType"
            }
            else -> "UnknownType" // Agregado para manejar tipos no conocidos
        }
}

data class IfElseNode(
    val condition: ExpressionNode,
    val ifBlock: BlockNode,
    val elseBlock: BlockNode? = null,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String {
        val ifBlockString = ifBlock.toFormattedString(variableTypes)
        val elseBlockString = elseBlock?.toFormattedString(variableTypes) ?: ""
        if (elseBlock == null) {
            return "if (${condition.toFormattedString(variableTypes)}) {\n$ifBlockString\n}"
        }
        return "if (${condition.toFormattedString(variableTypes)}) {\n$ifBlockString\n}\nelse {\n$elseBlockString\n}"
    }
}

data class BlockNode(
    val statements: List<StatementNode>,
    val line: Int,
    val column: Int,
) : StatementNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String =
        statements.joinToString(separator = "\n") { it.toFormattedString(variableTypes) }
}

data class BooleanLiteralNode(
    val value: Boolean,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = value.toString()
}

data class ReadInputNode(
    val value: String,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = "readInput($value)"

    override fun toString(): String = "ReadInputNode(value=$value, line=$line, column=$column)"
}

class ReadEnvNode(
    val value: Any, // El tipo de dato esperado: "string", "number" o "boolean"
    val line: Int, // Línea donde ocurre la llamada a readEnv
    val column: Int, // Columna donde ocurre la llamada a readEnv
) : ExpressionNode() { // Cambiamos de ExpressionNode a StatementNode
    override fun toFormattedString(variableTypes: Map<String, String>): String = "readEnv($value)"

    override fun toString(): String = "ReadEnvNode(value=$value, line=$line, column=$column)"
}

class NullValueNode(
    val defaultValue: Any,
    val line: Int,
    val column: Int,
) : ExpressionNode() {
    override fun toFormattedString(variableTypes: Map<String, String>): String = ""

    override fun toString(): String = "NullValueNode(line=$line, column=$column)"
}

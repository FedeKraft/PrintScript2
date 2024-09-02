package rules

import StatementNode
import ExpressionNode
import BinaryExpressionNode
import AssignationNode
import VariableDeclarationNode
import PrintStatementNode

class SpaceAroundOperatorsRule : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>): String {
        return when (node) {
            is AssignationNode -> {
                val leftPart = node.identifier.toString()
                val rightPart = formatExpression(node.value)
                "$leftPart = $rightPart;"
            }
            is VariableDeclarationNode -> {
                val identifierPart = node.identifier.toString()
                val valuePart = formatExpression(node.value)
                "$identifierPart: ${node.value} = $valuePart;"
            }
            is PrintStatementNode -> {
                "println(${formatExpression(node.expression)});"
            }
            else -> node.toString()  // Devuelve el nodo original si no coincide
        }
    }

    private fun formatExpression(expression: ExpressionNode): String {
        return when (expression) {
            is BinaryExpressionNode -> {
                val leftPart = expression.left.toString()
                val operatorPart = " ${expression.operator} "
                val rightPart = expression.right.toString()
                "$leftPart$operatorPart$rightPart"
            }
            else -> expression.toString()  // Si no es una expresión binaria, devolver como está
        }
    }
}

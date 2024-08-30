package interpreter

import VariableDeclarationNode
import AssignationNode
import PrintStatementNode
import ExpressionNode
import IdentifierNode
import StringLiteralNode
import NumberLiteralNode
import BinaryExpressionNode
import StatementNode
import token.TokenType

class Interpreter {

    private var context = ExecutionContext()

    // Método principal para interpretar una declaración
    fun interpret(statement: StatementNode) {
        interpretStatement(statement)
    }

    // Método para interpretar cada tipo de declaración (statement)
    private fun interpretStatement(statement: StatementNode) {
        when (statement) {
            is VariableDeclarationNode -> {
                val value = evaluateExpression(statement.value)
                context = context.addVariable(statement.identifier.name, value)
            }
            is AssignationNode -> {
                val value = evaluateExpression(statement.value)
                context = context.addVariable(statement.identifier.name, value)
            }
            is PrintStatementNode -> {
                val value = evaluateExpression(statement.expression)
                println(value)
            }
            else -> throw IllegalArgumentException("Tipo de declaración no soportada: ${statement::class.java.simpleName}")
        }
    }

    // Método para evaluar expresiones
    private fun evaluateExpression(expression: ExpressionNode): Any? {
        return when (expression) {
            is IdentifierNode -> context.getVariable(expression.name)
            is StringLiteralNode -> expression.value
            is NumberLiteralNode -> expression.value
            is BinaryExpressionNode -> {
                val leftValue = evaluateExpression(expression.left)
                val rightValue = evaluateExpression(expression.right)

                when (expression.operator) {
                    TokenType.SUM -> {
                        when {
                            leftValue is String && rightValue is String -> leftValue + rightValue
                            leftValue is Double && rightValue is Double -> leftValue + rightValue
                            leftValue is String && rightValue is Double -> leftValue + rightValue.toString()
                            leftValue is Double && rightValue is String -> leftValue.toString() + rightValue
                            else -> throw IllegalArgumentException(
                                "No soportada: ${leftValue?.javaClass?.name} y ${rightValue?.javaClass?.name}"
                            )
                        }
                    }
                    TokenType.SUBTRACT -> (leftValue as Double) - (rightValue as Double)
                    TokenType.MULTIPLY -> (leftValue as Double) * (rightValue as Double)
                    TokenType.DIVIDE -> (leftValue as Double) / (rightValue as Double)
                    else -> throw IllegalArgumentException("Operador binario inesperado: ${expression.operator}")
                }
            }
            else -> throw IllegalArgumentException("Tipo de expresión no soportada: ${expression::class.java.simpleName}")
        }
    }
}

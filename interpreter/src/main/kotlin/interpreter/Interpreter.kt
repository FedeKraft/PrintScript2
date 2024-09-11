package interpreter

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import parser.ASTProvider
import token.TokenType

class Interpreter(private val provider: ASTProvider) {

    private var context = ExecutionContext()

    // Método principal para interpretar una declaración
    fun interpret() {
        while (provider.hasNextAST()) {
            val statement = provider.getNextAST()
            interpretStatement(statement)
        }
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
            else -> throw IllegalArgumentException(
                "Tipo de declaración no soportada: ${statement::class.java.simpleName}",
            )
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
                        // Manejar concatenación de strings o suma de números
                        when {
                            leftValue is String || rightValue is String -> leftValue.toString().slice(
                                IntRange(0, leftValue.toString().length - 2),
                            ) + rightValue.toString().slice(IntRange(1, rightValue.toString().length - 1))
                            leftValue is Double && rightValue is Double -> leftValue + rightValue
                            else -> throw IllegalArgumentException(
                                "Operación de suma no soportada entre: ${leftValue?.javaClass?.name} " +
                                    "y ${rightValue?.javaClass?.name}",
                            )
                        }
                    }
                    TokenType.SUBTRACT -> {
                        if (leftValue is Double && rightValue is Double) {
                            leftValue - rightValue
                        } else {
                            throw IllegalArgumentException(
                                "Operación de resta no soportada para tipos:" +
                                    " ${leftValue?.javaClass?.name} y" +
                                    " ${rightValue?.javaClass?.name}",
                            )
                        }
                    }
                    TokenType.MULTIPLY -> {
                        if (leftValue is Double && rightValue is Double) {
                            leftValue * rightValue
                        } else {
                            throw IllegalArgumentException(
                                "Operación de multiplicación no soportada para tipos:" +
                                    " ${leftValue?.javaClass?.name} y " +
                                    "${rightValue?.javaClass?.name}",
                            )
                        }
                    }
                    TokenType.DIVIDE -> {
                        if (leftValue is Double && rightValue is Double) {
                            leftValue / rightValue
                        } else {
                            throw IllegalArgumentException(
                                "Operación de división no soportada para tipos:" +
                                    " ${leftValue?.javaClass?.name} y " +
                                    "${rightValue?.javaClass?.name}",
                            )
                        }
                    }
                    else -> throw IllegalArgumentException(
                        "Operador binario inesperado: " +
                            "${expression.operator}",
                    )
                }
            }
            else -> throw IllegalArgumentException(
                "Tipo de expresión no soportada: ${expression::class.java.simpleName}",
            )
        }
    }
}

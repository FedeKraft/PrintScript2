package interpreter

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.IfElseNode
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
                context.addVariable(statement.identifier.name, value)
            }
            is AssignationNode -> {
                val value = evaluateExpression(statement.value)
                context.addVariable(statement.identifier.name, value)
            }
            is PrintStatementNode -> {
                val value = evaluateExpression(statement.expression)
                println(value)
            }
            is IfElseNode -> {
                val condition = evaluateExpression(statement.condition) as? Boolean
                    ?: throw IllegalArgumentException("La condición del if debe ser un valor booleano (true o false)")

                if (condition) {
                    interpretStatement(statement.ifBlock)
                } else {
                    statement.elseBlock?.let { interpretStatement(it) }
                }
            }
            is BlockNode -> {
                // Entrar a un nuevo contexto para el bloque
                context.enterBlock()
                // Interpretar cada statement dentro del bloque
                statement.statements.forEach { interpretStatement(it) }
                // Salir del bloque y restaurar el contexto anterior
                context.exitBlock()
            }
            else -> throw IllegalArgumentException(
                "Tipo de declaración no soportada: ${statement::class.java.simpleName}",
            )
        }
    }

    // Método para evaluar expresiones
    private fun evaluateExpression(expression: ExpressionNode): Any {
        return when (expression) {
            is IdentifierNode -> {
                context.getVariable(expression.name)
                    ?: throw IllegalArgumentException("Variable '${expression.name}' no declarada.")
            }
            is StringLiteralNode -> expression.value
            is NumberLiteralNode -> expression.value

            is BooleanLiteralNode -> expression.value
            is BinaryExpressionNode -> {
                val leftValue = evaluateExpression(expression.left)
                val rightValue = evaluateExpression(expression.right)

                // Si uno de los operandos es String, realizar concatenación
                if (expression.operator == TokenType.SUM && (leftValue is String || rightValue is String)) {
                    return leftValue.toString() + rightValue.toString()
                }

                // Convertir ambos valores a Double si son Integer
                val leftAsDouble = when (leftValue) {
                    is Int -> leftValue.toDouble()
                    is Double -> leftValue
                    else -> throw IllegalArgumentException("Operación no soportada para tipo: ${leftValue.javaClass.name}")
                }

                val rightAsDouble = when (rightValue) {
                    is Int -> rightValue.toDouble()
                    is Double -> rightValue
                    else -> throw IllegalArgumentException("Operación no soportada para tipo: ${rightValue.javaClass.name}")
                }

                when (expression.operator) {
                    TokenType.SUM -> leftAsDouble + rightAsDouble
                    TokenType.SUBTRACT -> leftAsDouble - rightAsDouble
                    TokenType.MULTIPLY -> leftAsDouble * rightAsDouble
                    TokenType.DIVIDE -> {
                        if (rightAsDouble == 0.0) throw IllegalArgumentException("División por cero no permitida")
                        leftAsDouble / rightAsDouble
                    }
                    else -> throw IllegalArgumentException("Operador binario inesperado: ${expression.operator}")
                }
            }
            else -> throw IllegalArgumentException("Tipo de expresión no soportada: ${expression::class.java.simpleName}")
        }
    }
    fun getContext(): ExecutionContext {
        return context
    }
}

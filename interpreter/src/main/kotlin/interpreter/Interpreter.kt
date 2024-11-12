package interpreter

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.IfElseNode
import ast.NullValueNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import emitter.PrintEmitter
import errorCollector.ErrorCollector
import parser.ASTProvider
import provider.InputProvider
import token.TokenType

class Interpreter(
    private val provider: ASTProvider,
    private val inputProvider: InputProvider,
    private val printEmitter: PrintEmitter,
    private val errorCollector: ErrorCollector,
) {
    private var variables = ExecutionContext()
    private var constants = ExecutionContext()

    fun interpret(): List<Any?> {
        while (provider.hasNextAST()) {
            val statement = provider.getNextAST()
            try {
                interpretStatement(statement)
            } catch (e: Exception) {
                errorCollector.reportError("Error al interpretar la instrucción: ${e.message}")
            }
        }
        return printEmitter.getPrintedValues()
    }

    private fun interpretStatement(statement: StatementNode) {
        try {
            when (statement) {
                is VariableDeclarationNode -> {
                    val value = evaluateExpression(statement.value)
                    val inferredType = inferType(value)
                    if (!isTypeCompatible(inferredType, statement.type)) {
                        errorCollector.reportError(
                            "Error de tipo en declaración de variable: Se esperaba " +
                                "${statement.type} pero se encontró $inferredType",
                        )
                        return
                    }

                    variables.add(statement.identifier.name, value)
                }
                is ConstDeclarationNode -> {
                    val value = evaluateExpression(statement.value)
                    val inferredType = inferType(value)
                    if (!isTypeCompatible(inferredType, statement.type)) {
                        errorCollector.reportError(
                            "Error de tipo en declaración de constante: Se esperaba " +
                                "${statement.type} pero se encontró $inferredType",
                        )
                        return
                    }
                    constants.add(statement.identifier.name, value)
                }
                is AssignationNode -> {
                    if (constants.get(statement.identifier.name) != null) {
                        errorCollector.reportError("No se puede reasignar una constante: ${statement.identifier.name}")
                        return
                    }
                    val value = evaluateExpression(statement.value)
                    variables.add(statement.identifier.name, value)
                }
                is PrintStatementNode -> {
                    val value = evaluateExpression(statement.expression)
                    printEmitter.print(value!!)
                }
                is IfElseNode -> {
                    val condition = evaluateExpression(statement.condition) as? Boolean
                    if (condition == null) {
                        errorCollector.reportError("La condición del if debe ser un valor booleano")
                        return
                    }

                    if (condition) {
                        interpretStatement(statement.ifBlock)
                    } else {
                        statement.elseBlock?.let { interpretStatement(it) }
                    }
                }
                is BlockNode -> {
                    variables.enterBlock()
                    statement.statements.forEach { interpretStatement(it) }
                    variables.exitBlock()
                }
                else ->
                    errorCollector.reportError("Tipo de declaración no soportada: ${statement::class.java.simpleName}")
            }
        } catch (e: Exception) {
            errorCollector.reportError("Error en la declaración: ${e.message}")
        }
    }

    private fun evaluateExpression(expression: ExpressionNode): Any? {
        return try {
            when (expression) {
                is ReadInputNode -> {
                    println("Enter input for: ${expression.value}")
                    val input = inputProvider.readInput(expression.value)
                    println("Received input: $input")
                    input
                }
                is ReadEnvNode -> {
                    val envValue = System.getenv(expression.value as String)
                    envValue ?: errorCollector.reportError(
                        "La variable de entorno '${expression.value}' no está definida",
                    )
                }
                is IdentifierNode -> {
                    constants.get(expression.name) ?: variables.get(expression.name)
                        ?: errorCollector.reportError("Identificador no definido: ${expression.name}")
                }
                is StringLiteralNode -> expression.value
                is NumberLiteralNode -> expression.value
                is BooleanLiteralNode -> expression.value
                is NullValueNode -> expression.defaultValue
                is BinaryExpressionNode -> {
                    val leftValue = evaluateExpression(expression.left)
                    val rightValue = evaluateExpression(expression.right)

                    if (expression.operator == TokenType.SUM && (leftValue is String || rightValue is String)) {
                        return leftValue.toString() + rightValue.toString()
                    }

                    val leftAsDouble = leftValue as? Double ?: (leftValue as? Int)?.toDouble()
                    val rightAsDouble = rightValue as? Double ?: (rightValue as? Int)?.toDouble()

                    if (leftAsDouble == null || rightAsDouble == null) {
                        errorCollector.reportError(
                            "Operación no soportada entre los operandos: $leftValue y $rightValue",
                        )
                        return null
                    }

                    when (expression.operator) {
                        TokenType.SUM -> leftAsDouble + rightAsDouble
                        TokenType.SUBTRACT -> leftAsDouble - rightAsDouble
                        TokenType.MULTIPLY -> leftAsDouble * rightAsDouble
                        TokenType.DIVIDE -> {
                            if (rightAsDouble == 0.0) {
                                errorCollector.reportError("División por cero no permitida")
                                return null
                            }
                            leftAsDouble / rightAsDouble
                        }
                        else -> errorCollector.reportError("Operador binario inesperado: ${expression.operator}")
                    }
                }
                else -> errorCollector.reportError("Error al evaluar la expresión")
            }
        } catch (e: Exception) {
            errorCollector.reportError("Error al evaluar la expresión: ${e.message}")
            null
        }
    }

    private fun inferType(value: Any?): TokenType =
        when (value) {
            is String -> TokenType.STRING_TYPE
            is Double, is Int -> TokenType.NUMBER_TYPE
            is Boolean -> TokenType.BOOLEAN_TYPE
            is NumberLiteralNode -> TokenType.NUMBER_TYPE
            is StringLiteralNode -> TokenType.STRING_TYPE
            is BooleanLiteralNode -> TokenType.BOOLEAN_TYPE
            else -> throw IllegalArgumentException("Tipo desconocido: ${value?.javaClass?.simpleName}")
        }

    private fun isTypeCompatible(inferredType: TokenType, declaredType: TokenType): Boolean =
        inferredType == declaredType

    fun getPrintEmitter(): PrintEmitter = printEmitter
}

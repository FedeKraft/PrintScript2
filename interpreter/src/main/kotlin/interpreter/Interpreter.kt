package interpreter

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.IfElseNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import emitter.PrintEmitter
import parser.ASTProvider
import provider.InputProvider
import token.TokenType

class Interpreter(
    private val provider: ASTProvider,
    private val inputProvider: InputProvider,
    private val printEmitter: PrintEmitter,
) {

    private var variables = ExecutionContext()
    private var constants = ExecutionContext()

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
                val inferredType = inferType(value)

                // Verificamos que el tipo de la expresión coincida con el tipo declarado
                if (!isTypeCompatible(inferredType, statement.type)) {
                    throw IllegalArgumentException(
                        "Error de tipo: Se esperaba ${statement.type} pero se encontró $inferredType",
                    )
                }

                variables.add(statement.identifier.name, value)
            }
            is ConstDeclarationNode -> {
                val value = evaluateExpression(statement.value)
                constants.add(statement.identifier.name, value)
            }
            is AssignationNode -> {
                if (constants.get(statement.identifier.name) != null) {
                    throw IllegalArgumentException("No se puede reasignar una constante")
                }
                val value = evaluateExpression(statement.value)
                variables.add(statement.identifier.name, value)
            }
            is PrintStatementNode -> {
                val value = evaluateExpression(statement.expression)
                printEmitter.print(value)
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
                variables.enterBlock()
                // Interpretar cada statement dentro del bloque
                statement.statements.forEach { interpretStatement(it) }
                // Salir del bloque y restaurar el contexto anterior
                variables.exitBlock()
            }
            else -> throw IllegalArgumentException(
                "Tipo de declaración no soportada: ${statement::class.java.simpleName}",
            )
        }
    }

    // Método para evaluar expresiones
    private fun evaluateExpression(expression: ExpressionNode): Any {
        return when (expression) {
            is ReadInputNode -> {
                val input = inputProvider.readInput(expression.value) // Usamos el InputProvider
                return input
            }
            is ReadEnvNode -> {
                val envValue = System.getenv(expression.value as String)
                return envValue ?: throw IllegalArgumentException(
                    "La variable de entorno '${expression.value}' no esta definida",
                )
            }

            is IdentifierNode -> {
                val value = constants.get(expression.name) ?: variables.get(expression.name)
                value ?: throw IllegalArgumentException("Identificador no definido: ${expression.name}")
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
                    else -> throw IllegalArgumentException(
                        "Operación no soportada para tipo: ${leftValue.javaClass.name}",
                    )
                }

                val rightAsDouble = when (rightValue) {
                    is Int -> rightValue.toDouble()
                    is Double -> rightValue
                    else -> throw IllegalArgumentException(
                        "Operación no soportada para tipo: ${rightValue.javaClass.name}",
                    )
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
            else -> throw IllegalArgumentException(
                "Tipo de expresión no soportada: ${expression::class.java.simpleName}",
            )
        }
    }
    fun getContext(): ExecutionContext {
        return variables
    }
    private fun inferType(value: Any): TokenType {
        return when (value) {
            is String -> TokenType.STRING_TYPE
            is Double, is Int -> TokenType.NUMBER_TYPE
            is Boolean -> TokenType.BOOLEAN_TYPE
            else -> throw IllegalArgumentException("Tipo desconocido: ${value::class.java.simpleName}")
        }
    }
    private fun isTypeCompatible(inferredType: TokenType, declaredType: TokenType): Boolean {
        return inferredType == declaredType
    }
    fun getPrintEmitter(): PrintEmitter {
        return printEmitter
    }
}

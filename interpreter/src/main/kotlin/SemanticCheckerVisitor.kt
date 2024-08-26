import org.example.visitor.ASTVisitor

class SemanticCheckerVisitor(private var context: ExecutionContext) : ASTVisitor {

    override fun visit(programNode: ProgramNode) {
        for (statement in programNode.statements) {
            statement.accept(this)
        }
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode) {
        if (context.hasVariable(variableDeclarationNode.identifier.name)) {
            throw RuntimeException(
                "Error semántico: La variable '${variableDeclarationNode.identifier.name}' ya está declarada.",
            )
        }
        val value = evaluateExpression(variableDeclarationNode.value)
        context = context.addVariable(variableDeclarationNode.identifier.name, value)
    }

    override fun visit(assignationNode: AssignationNode) {
        if (!context.hasVariable(assignationNode.identifier.name)) {
            throw RuntimeException(
                "Error semántico: La variable '${assignationNode.identifier.name}' no está declarada.",
            )
        }
        val value = evaluateExpression(assignationNode.value)
        context = context.addVariable(assignationNode.identifier.name, value)
    }

    override fun visit(numberLiteralNode: NumberLiteralNode) {}

    override fun visit(binaryExpressionNode: BinaryExpressionNode) {
        val leftType = evaluateExpressionType(binaryExpressionNode.left)
        val rightType = evaluateExpressionType(binaryExpressionNode.right)
        if (binaryExpressionNode.operator == TokenType.SUM) {
            if (leftType != rightType) {
                throw RuntimeException("Error semántico: No se puede sumar $leftType con $rightType.")
            }
        } else {
            if (leftType != "Double" || rightType != "Double") {
                throw RuntimeException("Error semántico: Operación no permitida con tipos no numéricos.")
            }
        }
    }

    override fun visit(printStatementNode: PrintStatementNode) {
        printStatementNode.expression.accept(this)
    }

    override fun visit(identifierNode: IdentifierNode) {
        if (!context.hasVariable(identifierNode.name)) {
            throw RuntimeException("Error semántico: La variable '${identifierNode.name}' no está declarada.")
        }
    }

    override fun visit(stringLiteralNode: StringLiteralNode) {}

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
                                "No soportada: ${leftValue?.javaClass?.name} y ${rightValue?.javaClass?.name}",
                            )
                        }
                    }
                    TokenType.SUBTRACT -> (leftValue as Double) - (rightValue as Double)
                    TokenType.MULTIPLY -> (leftValue as Double) * (rightValue as Double)
                    TokenType.DIVIDE -> (leftValue as Double) / (rightValue as Double)
                    else -> throw IllegalArgumentException("Operador binario inesperado: ${expression.operator}")
                }
            }
            else -> throw IllegalArgumentException(
                "Tipo de expresión no soportada: ${expression::class.java.simpleName}",
            )
        }
    }

    private fun evaluateExpressionType(expression: ExpressionNode): String {
        return when (expression) {
            is IdentifierNode -> {
                val variable = context.getVariable(expression.name)
                variable?.javaClass?.simpleName ?: throw RuntimeException(
                    "Error semántico: La variable '${expression.name}' no está declarada.",
                )
            }
            is StringLiteralNode -> "String"
            is NumberLiteralNode -> "Double"
            is BinaryExpressionNode -> {
                val leftType = evaluateExpressionType(expression.left)
                val rightType = evaluateExpressionType(expression.right)
                if (leftType != rightType) {
                    throw RuntimeException("Error semántico: No se puede sumar $leftType con $rightType.")
                }
                leftType
            }
            else -> throw IllegalArgumentException(
                "Tipo de expresión no soportada: ${expression::class.java.simpleName}",
            )
        }
    }
}

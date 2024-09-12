package rules

import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import linter.LinterError

class ReadInputWithSimpleArgumentRule(
    override var isActive: Boolean = true,
) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        // Verificar si el nodo es una VariableDeclarationNode que contiene un ReadInputNode
        if (node is VariableDeclarationNode && node.value is ReadInputNode) {
            val readInputNode = node.value as ReadInputNode

            // Verificar si el argumento es un identificador o literal
            if (!isSimpleArgument(readInputNode)) {
                errors.add(
                    LinterError(
                        message = "readInput debe tener un argumento simple (identificador o literal)",
                        line = node.line,
                        column = node.column,
                    ),
                )
            }
        }
        return errors
    }

    // Verificar si el argumento es simple (identificador o literal)
    private fun isSimpleArgument(argument: ExpressionNode): Boolean =
        argument is IdentifierNode ||
            argument is StringLiteralNode ||
            argument is NumberLiteralNode ||
            argument is BooleanLiteralNode
}

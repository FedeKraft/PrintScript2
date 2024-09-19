package rules

import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import linter.LinterError

class PrintSimpleExpressionRule(
    override var isActive: Boolean = true,
) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        when (node) {
            is PrintStatementNode -> {
                val expression = node.expression

                // Check if the expression is either a simple identifier or a literal
                if (expression !is IdentifierNode && expression !is StringLiteralNode &&
                    expression !is NumberLiteralNode
                ) {
                    errors.add(
                        LinterError(
                            message = "Print statement too complex",
                            line = node.line,
                            column = node.column,
                        ),
                    )
                }
            }
            else -> {
                // No action needed for other types of StatementNode
            }
        }

        return errors
    }
}

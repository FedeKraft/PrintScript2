package rules

import ast.PrintStatementNode
import ast.StatementNode
import linter.LinterError

class PrintSimpleExpressionRule(override var isActive: Boolean = true) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        when (node) {
            is PrintStatementNode -> {
                if (node.expression.toFormattedString(emptyMap()).length > 40) {
                    errors.add(LinterError("Print statement too complex"))
                }
            }
            else -> {
                // No hacemos nada con otros tipos de StatementNode en esta regla
            }
        }

        return errors
    }
}

package rules

import LinterError
import PrintStatementNode
import StatementNode

class PrintSimpleExpressionRule(override var isActive: Boolean = true) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        when (node) {
            is PrintStatementNode -> {
                if (node.expression.toFormattedString(emptyMap()).length > 40) {
                    errors.add(LinterError("Print statement too complex", node.line, node.column))
                }
            }
            else -> {
                // No hacemos nada con otros tipos de StatementNode en esta regla
            }
        }

        return errors
    }
}

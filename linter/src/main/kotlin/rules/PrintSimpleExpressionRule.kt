package org.example.rules

import ASTNode
import BinaryExpressionNode
import org.example.LinterError

class PrintSimpleExpressionRule : LinterRule, ConfigurableRule {
    private var isEnabled = true

    override fun setConfig(config: Map<String, Boolean>) {
        isEnabled = config["print_simple_expression"] ?: true
    }

    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (!isEnabled) return errors

        if (node is BinaryExpressionNode) {
            errors.add(LinterError("Binary expression should be simple", node.line, node.column))
        }
        return errors
    }
}

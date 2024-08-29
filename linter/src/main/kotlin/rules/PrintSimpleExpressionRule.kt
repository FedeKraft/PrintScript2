package org.example.rules

import ASTNode
import BinaryExpressionNode
import PrintSimpleExpressionConfig
import org.example.LinterError

class PrintSimpleExpressionRule(private val config: PrintSimpleExpressionConfig) : LinterRule {


    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (!config.enabled) {
            return errors
        }
        if (node is BinaryExpressionNode) {
            errors.add(LinterError("Binary expression should be simple", node.line, node.column))
        }
        return errors
    }
}

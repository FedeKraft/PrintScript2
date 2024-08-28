package org.example.rules

import ASTNode
import BinaryExpressionNode
import PrintStatementNode
import org.example.LinterError

class PrintSimpleExpressionRule : LinterRule {
    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (node is PrintStatementNode) {
            val expression = node.expression
            if (expression is BinaryExpressionNode) {
                errors.add(LinterError("Binary expression should be simple", node.line, node.column))
            }
        }
        return errors
    }
}

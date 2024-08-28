package org.example.rules

import ASTNode
import BinaryExpressionNode

import org.example.LinterError

class PrintSimpleExpressionRule: LinterRule {
    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (node is BinaryExpressionNode){
            errors.add(LinterError("Binary expression should be simple", node.line, node.column))
        }
        return errors
    }
}
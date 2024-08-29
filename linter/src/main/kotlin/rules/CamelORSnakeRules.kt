package org.example.rules

import ASTNode
import org.example.LinterError

class CamelORSnakeRules : LinterRule {

    private val camelCaseRule = CamelCaseIdentifierRule()
    private val snakeCaseRule = SnakeCaseIdentifierRule()

    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        errors.addAll(camelCaseRule.check(node))
        errors.addAll(snakeCaseRule.check(node))
        return errors
    }
}

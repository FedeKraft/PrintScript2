package org.example.rules

import ASTNode
import IdentifierNode
import org.example.LinterError

class SnakeCaseIdentifierRule: LinterRule {
    private val snakeCaseRegex = Regex("^[a-z]+(_[a-z]+)*$")
    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (node is IdentifierNode) {
            if (!node.name.matches(snakeCaseRegex)) {
                errors.add(LinterError("Identifier ${node.name} should be in snake case", node.line, node.column))
            }
        }
        return errors
    }
}
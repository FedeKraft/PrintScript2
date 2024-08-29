package org.example.rules

import ASTNode
import CamelCaseIdentifierConfig
import IdentifierNode
import org.example.LinterError

class CamelCaseIdentifierRule: LinterRule {
    override fun check(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (node is IdentifierNode) {
            if (!node.name.matches(Regex("^[a-z]+([A-Z][a-z]*)*$"))) {
                errors.add(LinterError("Identifier ${node.name} should be in camel case", node.line, node.column))
            }
        }
        return errors
    }
}

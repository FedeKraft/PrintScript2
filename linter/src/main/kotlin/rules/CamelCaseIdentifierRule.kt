package rules

import AssignationNode
import IdentifierNode
import LinterError
import PrintStatementNode
import StatementNode
import VariableDeclarationNode


class CamelCaseIdentifierRule(override var isActive: Boolean = true) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        when (node) {
            is VariableDeclarationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            is AssignationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            is PrintStatementNode -> {
                // No hacemos nada con PrintStatementNode en esta regla
            }
        }

        return errors
    }

    private fun checkIdentifier(identifier: IdentifierNode, errors: MutableList<LinterError>) {
        if (!identifier.name.matches(Regex("^[a-z]+([A-Z][a-z]*)*$"))) {
            errors.add(LinterError("Identifier '${identifier.name}' should be in camelCase", identifier.line, identifier.column))
        }
    }
}

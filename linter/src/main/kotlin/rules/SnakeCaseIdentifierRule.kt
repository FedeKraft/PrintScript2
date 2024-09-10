package rules

import ast.AssignationNode
import ast.IdentifierNode
import ast.StatementNode
import ast.VariableDeclarationNode
import linter.LinterError

class SnakeCaseIdentifierRule(override var isActive: Boolean = true) : LinterRule {
    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        when (node) {
            is VariableDeclarationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            is AssignationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            else -> {
                // No hacemos nada con otros tipos de StatementNode en esta regla
            }
        }

        return errors
    }

    private fun checkIdentifier(identifier: IdentifierNode, errors: MutableList<LinterError>) {
        if (!identifier.name.matches(Regex("^[a-z]+(_[a-z]+)*$"))) {
            errors.add(
                LinterError(
                    "Identifier '${identifier.name}' should be in snake_case",
                ),
            )
        }
    }
}

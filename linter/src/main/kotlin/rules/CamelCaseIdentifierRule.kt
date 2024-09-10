package rules

import ast.AssignationNode
import ast.IdentifierNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.VariableDeclarationNode
import linter.LinterError

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
            errors.add(
                LinterError(
                    "Identifier '${identifier.name}' should be in camelCase",
                    identifier.line,
                    identifier.column,
                ),
            )
        }
    }
}

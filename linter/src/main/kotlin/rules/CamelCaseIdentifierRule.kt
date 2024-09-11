package rules

import ast.AssignationNode
import ast.BlockNode
import ast.ConstDeclarationNode
import ast.IdentifierNode
import ast.IfElseNode
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

            is BlockNode -> TODO()
            is ConstDeclarationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            is IfElseNode -> TODO()
        }

        return errors
    }

    private fun checkIdentifier(identifier: IdentifierNode, errors: MutableList<LinterError>) {
        if (!identifier.name.matches(Regex("^[a-z]+([A-Z][a-z]*)*$"))) {
            errors.add(
                LinterError(
                    message = "Identifier '${identifier.name}' should be in camelCase",
                    line = identifier.line,
                    column = identifier.column,
                ),
            )
        }
    }
}

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

class CamelCaseIdentifierRule(
    override var isActive: Boolean = true,
) : LinterRule {
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
            is BlockNode -> {
                node.statements.forEach { statement ->
                    errors.addAll(apply(statement)) // Aplicamos la regla a todas las sentencias dentro del bloque
                }
            }
            is ConstDeclarationNode -> {
                checkIdentifier(node.identifier, errors)
            }
            is IfElseNode -> {
                // Aplicar la regla al bloque if
                errors.addAll(apply(node.ifBlock))

                // Si hay un bloque else, aplicar la regla también
                node.elseBlock?.let { elseBlock ->
                    errors.addAll(apply(elseBlock))
                }
            }
        }

        return errors
    }

    private fun checkIdentifier(
        identifier: IdentifierNode,
        errors: MutableList<LinterError>,
    ) {
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

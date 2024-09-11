package rules

import ast.StatementNode
import ast.VariableDeclarationNode
import linter.LinterError

class CamelORSnakeRules(
    private val camelCaseRule: CamelCaseIdentifierRule,
    private val snakeCaseRule: SnakeCaseIdentifierRule,
    override var isActive: Boolean = true,
) : LinterRule {

    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()

        // Si el identificador parece camelCase, aplicamos solo la regla de camelCase
        if (node is VariableDeclarationNode && node.identifier.name.matches(Regex("^[a-z]+([A-Z][a-z]+)*$"))) {
            if (camelCaseRule.isActive) {
                errors.addAll(camelCaseRule.apply(node))
            }
        }
        // Si el identificador parece snake_case, aplicamos solo la regla de snake_case
        else if (node is VariableDeclarationNode && node.identifier.name.matches(Regex("^[a-z]+(_[a-z]+)*$"))) {
            if (snakeCaseRule.isActive) {
                errors.addAll(snakeCaseRule.apply(node))
            }
        }

        return errors
    }
}

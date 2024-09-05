package rules

import LinterError
import StatementNode

class CamelORSnakeRules(
    private val camelCaseRule: CamelCaseIdentifierRule,
    private val snakeCaseRule: SnakeCaseIdentifierRule,
    override var isActive: Boolean = true,
) : LinterRule {

    override fun apply(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        if (camelCaseRule.isActive) {
            errors.addAll(camelCaseRule.apply(node))
        }
        if (snakeCaseRule.isActive) {
            errors.addAll(snakeCaseRule.apply(node))
        }
        return errors
    }
}

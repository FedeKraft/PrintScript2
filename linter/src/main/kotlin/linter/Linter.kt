package linter

import ast.StatementNode
import org.example.parser.ASTProvider
import rules.LinterRule

class Linter(
    private val rules: List<LinterRule>,
    private val astProvider: ASTProvider,
) {
    fun lint(): Sequence<LinterError> = sequence {
        while (astProvider.hasNextAST()) {
            val node = astProvider.getNextAST()
            val errors = applyRules(node)
            errors.forEach { yield(it) }
        }
    }

    private fun applyRules(node: StatementNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        for (rule in rules.filter { it.isActive }) {
            val ruleErrors = rule.apply(node)
            errors.addAll(ruleErrors)
        }
        return errors
    }
}

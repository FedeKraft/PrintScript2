package rules

import ast.StatementNode

interface FormatterRule {
    fun applyRule(
        node: StatementNode,
        result: String,
    ): String
}

package rules

import ast.StatementNode

interface FormatterRule {
    fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String
}

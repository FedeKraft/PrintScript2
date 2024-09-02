package rules

import StatementNode

interface FormatterRule {
    fun applyRule(node: StatementNode, variableTypes: Map<String, Any>): String
}

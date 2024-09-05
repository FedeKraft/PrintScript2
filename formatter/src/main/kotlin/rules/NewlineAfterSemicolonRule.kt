package rules

import StatementNode

class NewlineAfterSemicolonRule : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        return if (node.toString().trimEnd().endsWith(";")) {
            node.toString().trimEnd() + "\n"
        } else {
            node.toString()
        }
    }
}

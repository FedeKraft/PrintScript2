package rules

import StatementNode

class SingleSpaceBetweenTokensRule : FormatterRule {
    override fun applyRule(node: StatementNode,variableTypes: Map<String, Any>): String {
        val formatted = node.toString().replace("\\s+".toRegex(), " ")
        return formatted.trim()  // Asegura que no haya espacios al inicio o final
    }
}

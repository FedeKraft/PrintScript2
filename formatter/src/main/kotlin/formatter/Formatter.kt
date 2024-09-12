package formatter

import ast.StatementNode
import parser.ASTProvider
import rules.FormatterRule

class Formatter(
    private val rules: List<FormatterRule>,
    private val astProvider: ASTProvider,
) {
    private val variableTypes = mutableMapOf<String, String>()

    fun format(): Sequence<String> =
        sequence {
            while (astProvider.hasNextAST()) {
                val node = astProvider.getNextAST()
                val formattedString = applyRules(node)
                if (formattedString.isNotEmpty()) {
                    yield(formattedString)
                }
            }
        }

    private fun applyRules(node: StatementNode): String {
        var result = node.toFormattedString(variableTypes)
        for (rule in rules) {
            result = rule.applyRule(node, variableTypes, result)
        }
        return result
    }
}

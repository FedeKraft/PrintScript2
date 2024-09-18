package rules

import ast.PrintStatementNode
import ast.StatementNode

class SpaceBeforeColonRule(
    private val enabled: Boolean,
) : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        if (node !is PrintStatementNode) {
            val modifiedResult = StringBuilder()
            for (i in result.indices) {
                if (result[i] == ':') {
                    if (enabled && i > 0 && result[i - 1] != ' ') {
                        modifiedResult.append(' ')
                    } else if (!enabled && i > 0 && result[i - 1] == ' ') {
                        modifiedResult.deleteCharAt(modifiedResult.length - 1)
                    }
                }
                modifiedResult.append(result[i])
            }
            return modifiedResult.toString()
        }
        return result // Devuelve el nodo original si no aplica
    }
}

package rules

import ast.StatementNode
import ast.VariableDeclarationNode

class SpaceAfterColonRule(
    private val enabled: Boolean,
) : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        if (node is VariableDeclarationNode) {
            val modifiedResult = StringBuilder()
            for (i in result.indices) {
                if (result[i] == ':') {
                    modifiedResult.append(result[i])
                    if (enabled && i < result.length - 1 && result[i + 1] != ' ') {
                        modifiedResult.append(' ')
                    }
                } else {
                    modifiedResult.append(result[i])
                }
            }
            return modifiedResult.toString()
        }
        return result
    }
}

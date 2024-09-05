package rules

import StatementNode
import AssignationNode

class SpaceAroundEqualsRule(private val enabled: Boolean) : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        val modifiedResult = StringBuilder()
        var i = 0

        while (i < result.length) {
            val char = result[i]
            if (char == '=') {
                if (i > 0 && result[i - 1] != ' ') {
                    modifiedResult.append(' ')
                }
                modifiedResult.append(char)
                if (i < result.length - 1 && result[i + 1] != ' ') {
                    modifiedResult.append(' ')
                }
            } else {
                modifiedResult.append(char)
            }
            i++
        }
        return modifiedResult.toString()
    }
}

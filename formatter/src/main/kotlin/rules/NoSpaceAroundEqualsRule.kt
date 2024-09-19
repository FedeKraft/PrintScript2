package rules

import ast.StatementNode

class NoSpaceAroundEqualsRule(
    private val enabled: Boolean,
) : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        val modifiedResult = StringBuilder()
        var i = 0
        while (i < result.length) {
            val char = result[i]
            if (char == '=') {
                if (enabled) {
                    // Remove spaces before and after '='
                    if (i > 0 && result[i - 1] == ' ') {
                        modifiedResult.deleteCharAt(modifiedResult.length - 1)
                    }
                    modifiedResult.append(char)
                    i++
                    while (i < result.length && result[i] == ' ') {
                        i++
                    }
                    continue
                } else {
                    // Add spaces around '=' if not present
                    if (i > 0 && result[i - 1] != ' ') {
                        modifiedResult.append(' ')
                    }
                    modifiedResult.append(char)
                    if (i < result.length - 1 && result[i + 1] != ' ') {
                        modifiedResult.append(' ')
                    }
                }
            } else {
                modifiedResult.append(char)
            }
            i++
        }
        return modifiedResult.toString()
    }
}

package rules

import ast.StatementNode

class SingleSpaceBetweenTokensRule : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        val modifiedResult = StringBuilder()
        var i = 0

        while (i < result.length) {
            val char = result[i]
            if (char.isLetterOrDigit()) {
                modifiedResult.append(char)
                if (i < result.length - 1 && !result[i + 1].isLetterOrDigit()) {
                    modifiedResult.append(' ')
                }
            } else {
                modifiedResult.append(char)
                if (i < result.length - 1 && result[i + 1].isLetterOrDigit()) {
                    modifiedResult.append(' ')
                }
            }
            i++
        }
        return modifiedResult.toString().replace("\\s+".toRegex(), " ").trim()
    }
}

package rules

import ast.IfElseNode
import ast.PrintStatementNode
import ast.StatementNode

class SingleSpaceBetweenTokensRule : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        if (node is PrintStatementNode) {
            // Special handling for PrintStatementNode to avoid separating println from its arguments
            return result.replace("\\s+".toRegex(), " ").trim()
        }
        if (node is IfElseNode) {
            return result
        }

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
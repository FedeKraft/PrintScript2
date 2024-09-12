package rules

import ast.IfElseNode
import ast.StatementNode
import config.Indentation

class  Indentation(private val config: Indentation) : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
    if (node !is IfElseNode) {
        return result
    }

    val modifiedResult = StringBuilder()
    var indentationLevel = config.n
    var i = 0
    var insideBraces = false

    while (i < result.length) {
        val char = result[i]
        when (char) {
            '{' -> {
                modifiedResult.append(" {")
                insideBraces = true
                indentationLevel++
            }
            '}' -> {
                modifiedResult.append('\n')
                indentationLevel--
                modifiedResult.append("    ".repeat(indentationLevel))
                modifiedResult.append(char)
                insideBraces = false
            }
            ';' -> {
                modifiedResult.append(char)
                if (insideBraces) {
                    modifiedResult.append('\n')
                    modifiedResult.append("    ".repeat(indentationLevel))
                }
            }
            else -> {
                if (char == '\n' && insideBraces) {
                    modifiedResult.append('\n')
                    modifiedResult.append("    ".repeat(indentationLevel))
                } else {
                    modifiedResult.append(char)
                }
            }
        }
        i++
    }
    return modifiedResult.toString()
}
}
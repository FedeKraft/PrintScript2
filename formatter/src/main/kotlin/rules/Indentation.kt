package rules

import ast.IfElseNode
import ast.StatementNode
import config.Indentation

class Indentation(private val config: Indentation) : FormatterRule {

    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        if (node !is IfElseNode) {
            return result
        }
        val indentations = " ".repeat(config.n)
        val lines = result.split("\n")
        val formattedLines = mutableListOf<String>()
        formattedLines.add(lines[0].trim())
        for (i in 1 until lines.size - 1) {
            if (lines[i].trim() == "}") {
                formattedLines.add(lines[i].trim())
            } else if (lines[i].trim().startsWith("else")) {
                formattedLines.add(lines[i].trim())
            } else {
                formattedLines.add("$indentations${lines[i].trim()}")
            }
        }
        formattedLines.add(lines.last().trim())
        return formattedLines.joinToString("\n")
    }
}

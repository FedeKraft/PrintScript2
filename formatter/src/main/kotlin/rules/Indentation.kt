package rules

import ast.StatementNode
import config.Indentation

class Indentation(private val config: Indentation) : FormatterRule {

    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        val lines = result.split("\n")
        val formattedLines = mutableListOf<String>()
        var currentIndentation = 0

        for (line in lines) {
            val trimmedLine = line.trim()
            when {
                trimmedLine.startsWith("if") || trimmedLine.startsWith("else") -> {
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                    if (trimmedLine.endsWith("{")) {
                        currentIndentation += config.n
                    }
                }
                trimmedLine == "}" -> {
                    currentIndentation -= config.n
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                }
                else -> {
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                }
            }
        }
        return formattedLines.joinToString("\n")
    }
}

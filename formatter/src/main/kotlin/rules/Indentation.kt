package rules

import ast.StatementNode

class Indentation(private val n: Int) : FormatterRule {

    override fun applyRule(node: StatementNode, result: String): String {
        val lines = result.split("\n")
        val formattedLines = mutableListOf<String>()
        var currentIndentation = 0

        println("Aplicando regla de indentación con tamaño: $n")
        println("Código original:\n$result")

        for (line in lines) {
            val trimmedLine = line.trim()
            println("Procesando línea: '$trimmedLine' (indentación actual: $currentIndentation)")

            when {
                // Inicio de un bloque, aumenta la indentación
                trimmedLine.startsWith("if") || trimmedLine.startsWith("else") -> {
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                    if (trimmedLine.endsWith("{")) {
                        currentIndentation += n
                        println("Bloque detectado. Aumentando indentación a: $currentIndentation")
                    }
                }
                // Cierre de un bloque, reduce la indentación
                trimmedLine == "}" -> {
                    currentIndentation -= n
                    currentIndentation = currentIndentation.coerceAtLeast(0) // Evita indentaciones negativas
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                    println("Cierre de bloque detectado. Reduciendo indentación a: $currentIndentation")
                }
                // Línea normal
                else -> {
                    formattedLines.add(" ".repeat(currentIndentation) + trimmedLine)
                }
            }
        }

        val formattedResult = formattedLines.joinToString("\n")
        println("Resultado formateado:\n$formattedResult")
        return formattedResult
    }
}

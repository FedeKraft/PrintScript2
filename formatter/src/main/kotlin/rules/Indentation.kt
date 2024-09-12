package rules

import ast.IfElseNode
import ast.StatementNode
import config.Indentation

class Indentation(private val config: Indentation) : FormatterRule {

    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        if (node !is IfElseNode) {
            return result
        }

        // Obtenemos la cantidad de indentaciones desde la configuración
        val indentations = " ".repeat(config.n)

        // Tomamos el cuerpo del if y lo dividimos por líneas para procesarlas.
        val lines = result.split("\n")
        val formattedLines = mutableListOf<String>()

        // Agregamos la primera línea del if sin cambios
        formattedLines.add(lines[0].trim())

        // Aplicamos la indentación al cuerpo del if, pero manteniendo el 'else' alineado con el 'if'
        for (i in 1 until lines.size - 1) {
            if (lines[i].trim() == "}") {
                // Alineamos el brace de cierre con el 'if' y el 'else'
                formattedLines.add(lines[i].trim())
            } else if (lines[i].trim().startsWith("else")) {
                // Aseguramos que el else esté alineado con el if
                formattedLines.add(lines[i].trim())
            } else {
                // Indentamos normalmente el cuerpo del bloque
                formattedLines.add("$indentations${lines[i].trim()}")
            }
        }

        // Agregamos la última línea con el brace de cierre o el else
        formattedLines.add(lines.last().trim())

        // Unimos las líneas ya formateadas
        return formattedLines.joinToString("\n")
    }
}

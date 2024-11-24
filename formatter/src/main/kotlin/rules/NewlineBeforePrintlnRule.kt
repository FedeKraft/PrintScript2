package rules

import ast.PrintStatementNode
import ast.StatementNode

class NewlineBeforePrintlnRule(
    private val n: Int, // Número de líneas en blanco antes de "println"
) : FormatterRule {

    init {
        // Validar en el momento de instanciar la regla
        require(n in 0..2) { "Newline count must be between 0 and 2 inclusive" }
    }

    override fun applyRule(
        node: StatementNode,
        result: String,
    ): String {
        println("Aplicando NewlineBeforePrintlnRule con n=$n")
        println("Nodo recibido: ${node::class.simpleName}, Resultado previo: '$result'")

        // Solo aplica la regla si el nodo es un PrintStatementNode
        if (node is PrintStatementNode) {
            val formattedStatement = StringBuilder()

            // Añadir líneas en blanco antes si n > 0
            if (n > 0) {
                repeat(n) {
                    formattedStatement.append("\n")
                }
                println("Se añadieron $n líneas en blanco antes de la instrucción 'println'")
            }

            // Añadir el resultado actual después de las líneas en blanco
            formattedStatement.append(result)

            // Mostrar el resultado final formateado
            val formattedResult = formattedStatement.toString()
            println("Resultado formateado: '$formattedResult'")
            return formattedResult
        }

        // Si no es un nodo PrintStatementNode, retornar el resultado sin cambios
        return result
    }
}

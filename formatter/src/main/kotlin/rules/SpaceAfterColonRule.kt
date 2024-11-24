package rules

import ast.StatementNode

class SpaceAfterColonRule(
    private val enabled: Boolean,
) : FormatterRule {

    override fun applyRule(
        node: StatementNode,
        result: String,
    ): String {
        println("Aplicando SpaceAfterColonRule con enabled = $enabled")
        println("Código antes de aplicar la regla:\n$result")

        // Regex para buscar ':' sin el espacio correcto después
        val regex = if (enabled) {
            """(:)(?!\s)""".toRegex() // ':' no seguido de un espacio
        } else {
            """(:)\s""".toRegex() // ':' seguido de un espacio
        }

        // Aplicar la regla según el estado de `enabled`
        val formattedResult = if (enabled) {
            result.replace(regex, "$1 ") // Asegurar un espacio después de ':'
        } else {
            result.replace(regex, "$1") // Remover el espacio después de ':'
        }

        println("Código después de aplicar la regla:\n$formattedResult")
        return formattedResult
    }
}

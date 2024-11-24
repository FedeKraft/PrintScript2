package rules

import ast.StatementNode

class SpaceBeforeColonRule(
    private val enabled: Boolean,
) : FormatterRule {

    override fun applyRule(
        node: StatementNode,
        result: String,
    ): String {
        println("Aplicando SpaceBeforeColonRule con enabled = $enabled")
        println("Código antes de aplicar la regla:\n$result")

        // Regex para manejar espacios antes del carácter ':'
        val regex = if (enabled) {
            """(?<!\s):""".toRegex() // ':' no precedido por un espacio
        } else {
            """\s+:""".toRegex() // ':' precedido por un espacio
        }

        val formattedResult = if (enabled) {
            result.replace(regex, " :") // Asegurar un espacio antes de ':'
        } else {
            result.replace(regex, ":") // Remover el espacio antes de ':'
        }

        println("Código después de aplicar la regla:\n$formattedResult")
        return formattedResult
    }
}

package rules

import ast.StatementNode

class NoSpaceAroundEqualsRule(
    private val enabled: Boolean, // Si `true`, elimina espacios alrededor del '=', de lo contrario, los agrega.
) : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        result: String,
    ): String {
        println("Aplicando NoSpaceAroundEqualsRule con enabled = $enabled")
        println("Código antes de la regla:\n$result")

        val regex = if (enabled) {
            // Regex para encontrar '=' rodeado de espacios
            """\s*=\s*""".toRegex()
        } else {
            // Regex para encontrar '=' sin espacios o con espacios incorrectos
            """(?<!\s)=(?!\s)""".toRegex()
        }

        val formattedResult = if (enabled) {
            // Si está habilitada, elimina todos los espacios alrededor del '='
            result.replace(regex, "=")
        } else {
            // Si no está habilitada, asegura que haya un espacio antes y después del '='
            result.replace(regex, " = ")
        }

        println("Código después de la regla:\n$formattedResult")
        return formattedResult
    }
}

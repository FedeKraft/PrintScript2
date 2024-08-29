package org.example.rules

import ASTNode

class SpaceAroundSemicolonRule : FormatterRule {
    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        // Usamos una expresión regular para reemplazar los espacios antes y después del `;`
        val regex = Regex("\\s*;\\s*")
        val formattedCode = regex.replace(code.toString(), ";")
        return StringBuilder(formattedCode)
    }
}

package org.example.rules

import ASTNode

class NewlineAfterSemicolonRule : FormatterRule {
    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        var index = 0

        while (index < code.length) {
            if (code[index] == ';') {
                // Verificar si hay texto después del ; en la misma línea
                if (index + 1 < code.length && code[index + 1] != '\n') {
                    code.insert(index + 1, '\n')
                }
            }
            index++
        }

        return code
    }
}
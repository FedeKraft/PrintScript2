package org.example.rules

import ASTNode

class SpaceBeetwenOperatorsRule : FormatterRule {
    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        var index = 0

        while (index < code.length) {
            if (code[index] == '+' || code[index] == '-' || code[index] == '*' || code[index] == '/') {
                // Verificar si hay espacio antes del operador
                if (index - 1 >= 0 && code[index - 1] != ' ') {
                    code.insert(index, ' ')
                    index++
                }
                // Verificar si hay espacio después del operador
                if (index + 1 < code.length && code[index + 1] != ' ') {
                    code.insert(index + 1, ' ')
                    index++
                }
            }
            index++
        }

        return code
    }
}
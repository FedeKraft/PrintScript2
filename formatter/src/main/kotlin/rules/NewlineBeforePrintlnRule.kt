package org.example.rules

import ASTNode

class NewlineBeforePrintlnRule(
    newlineCount: Int
) : FormatterRule {
    var newlineCount: Int = newlineCount
        set(value) {
            require(value in 0..2) { "newlineCount must be 0, 1, or 2" }
            field = value
        }

    init {
        this.newlineCount = newlineCount
    }

    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        // Encontrar la primera aparición de "println("
        var printlnIndex = code.indexOf("println(")

        // Contar saltos de línea antes de println
        var newlineBeforeCount = 0
        var currentIndex = printlnIndex - 1

        while (currentIndex >= 0 && code[currentIndex] == '\n') {
            newlineBeforeCount++
            currentIndex--
        }

        // Ajustar el número de saltos de línea
        while (newlineBeforeCount > newlineCount) {
            code.deleteCharAt(printlnIndex - 1)
            printlnIndex--
            newlineBeforeCount--
        }

        while (newlineBeforeCount < newlineCount) {
            code.insert(printlnIndex, '\n')
            printlnIndex++
            newlineBeforeCount++
        }

        return code
    }
}
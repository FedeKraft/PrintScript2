package org.example.rules

import ASTNode
import NewlineBeforePrintlnConfig

class NewlineBeforePrintlnRule(private val config: NewlineBeforePrintlnConfig) : FormatterRule {

    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        if (!config.enabled) {
            return code
        }

        val printlnIndex = code.indexOf("println(")
        if (printlnIndex == -1) return code

        var newlineBeforeCount = 0
        var currentIndex = printlnIndex - 1

        while (currentIndex >= 0 && code[currentIndex] == '\n') {
            newlineBeforeCount++
            currentIndex--
        }

        // Ajusta los saltos de línea antes de `println` según `config.newlineCount`
        while (newlineBeforeCount > config.newlineCount) {
            code.deleteCharAt(printlnIndex - 1)
            newlineBeforeCount--
        }

        while (newlineBeforeCount < config.newlineCount) {
            code.insert(printlnIndex, '\n')
            newlineBeforeCount++
        }

        return code
    }
}
